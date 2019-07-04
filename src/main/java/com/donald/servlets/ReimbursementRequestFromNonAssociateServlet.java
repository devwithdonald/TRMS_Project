package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.services.ValidationServiceImpl;
import com.donald.util.LoggingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReimbursementRequestFromNonAssociateServlet extends HttpServlet {

	private static ValidationServiceImpl vsi = new ValidationServiceImpl();
	private static ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	Employee loggedInEmployee;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession sess = req.getSession(false);
		if (sess == null || sess.getAttribute("employee") == null) {
			req.getRequestDispatcher("login").forward(req, resp);
			// need to return so the rest of the method doesn't run
			LoggingUtil.warn(
					"Reimbursement Request Form - non associate -> Tried to reach access by non logged in user. Successfully redirected.");
			return;
		} else {
			// send to appropriate site
			sess = req.getSession();
			loggedInEmployee = (Employee) sess.getAttribute("employee");
			// resp.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee) + ".html");
		}

		// returning balance
		resp.getWriter().write(String.valueOf(loggedInEmployee.getAvailableBalance()));

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoggingUtil.trace("in doPost(); for ReimbursementRequestFormServlet - NON ASSOCIATE");

		// login verification
		HttpSession sess = req.getSession(false);
		if (sess == null || sess.getAttribute("employee") == null) {
			// changing!!!
			// req.getRequestDispatcher("login").forward(req, resp);
			resp.sendRedirect("login");
			// need to return so the rest of the method doesn't run
			LoggingUtil.warn(
					"Reimbursement Request Form -> Tried to reach access by non logged in user. Successfully redirected.");
			return;
		}

		// get employee
		loggedInEmployee = (Employee) sess.getAttribute("employee");

		String body = req.getReader().readLine();
		LoggingUtil.debug("sent contents -> " + body);
		ObjectMapper om = new ObjectMapper();
		ReimbursementRequest reimbursementRequest = om.readValue(body, ReimbursementRequest.class);
		
		
		
		//if cost will take employee below
		//should be in another ????
		if (!vsi.balanceVerification(loggedInEmployee, reimbursementRequest)) {
			resp.getWriter().write("Cost Invalid - This will take your balance below 0");
			LoggingUtil.debug("Cost Invalid");
			return;
		}
		

		if (vsi.dateCheck(reimbursementRequest.getDateOfEvent()) == true) {

			ReimbursementRequest reimbursementRequestCheck = rsi.insertReimbursementRequest(loggedInEmployee, reimbursementRequest);
	

			// if null is sent back send back error
			if (reimbursementRequestCheck == null) {
				// send response if failed login
				// resp.setStatus(500);
				resp.getWriter().write("Failed to insert reimbursement request");

				LoggingUtil.debug("Failed to insert reimbursement request");
			} else {
				
				//updating employee?
				loggedInEmployee.setPendingBalance(loggedInEmployee.getPendingBalance() + rsi.calculateAwardByReimbursementType(reimbursementRequest.getEventType(), reimbursementRequest.getCost()));
				sess.setAttribute("employee", loggedInEmployee);
			
				resp.getWriter().write("Reimbursement request successful!");
				LoggingUtil.debug("Reimbursement request successful");
			}
		} else {
			resp.getWriter().write("Date Invalid - Must be 7 days after current date.");
			LoggingUtil.debug("Date Invalid");
		}
	}
}
