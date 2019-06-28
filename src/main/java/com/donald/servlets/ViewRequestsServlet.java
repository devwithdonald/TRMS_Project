package com.donald.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Associate;
import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ViewRequestsServlet extends HttpServlet{
	
	ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Employee loggedInEmployee;
		HttpSession sess = req.getSession(false);
		if (sess == null || sess.getAttribute("employee") == null) {
			req.getRequestDispatcher("login").forward(req, resp);
			// need to return so the rest of the method doesn't run
			LoggingUtil.warn("Tried to reach View Requests access by non logged in user. Successfully redirected.");
			return;
		} else {
			//send to appropriate site
			sess = req.getSession();
			loggedInEmployee = (Employee) sess.getAttribute("employee");
			//need to send if associate
			if (loggedInEmployee instanceof Associate) {
				resp.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee));
				return;
			} 
		}
		
		//might be wrong?
		resp.sendRedirect("view_requests.html");
		
		//getting reimbursements
		//TODO may need to do switch case statements depending on whos asking!
		List<ReimbursementRequest> reimbursementRequestList = rsi.viewPendingReimbursementRequests(loggedInEmployee);
		
		ObjectMapper om = new ObjectMapper();
		String reimbursementListString = om.writeValueAsString(reimbursementRequestList);
		resp.getWriter().write(reimbursementListString);
		
		
		

	}
	
	
	// send acceptance or denial requests 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//populate the table!
	}
	
	
}
