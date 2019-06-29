package com.donald.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UpdateRequestsServlet
 */
public class UpdateRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	Employee loggedInEmployee;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRequestsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoggingUtil.debug("in doGet(); for UpdateRequestServlet");
		//TODO NEED EMPLOYEE VERIFICAITON!!!!!!
		
		HttpSession sess = request.getSession(false);
		
//		if(sess != null && sess.getAttribute("employee") != null) {
//			//user is logged in send to home page
			loggedInEmployee = (Employee) sess.getAttribute("employee");
//			LoggingUtil.warn(loggedInEmployee.getUsername() + " tried to access the login page while logged in. Redirected succesfully.");
//			response.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee));
//		} else {
//			response.sendRedirect("login.html");
//		}
		
		List<ReimbursementRequest> reimbursementRequestList = rsi.viewPersonalPendingReimbursementRequests(loggedInEmployee);
		
		ObjectMapper om = new ObjectMapper();
		String reimbursementListString = om.writeValueAsString(reimbursementRequestList);
		LoggingUtil.info("Pulled requests JSON as String-> " + reimbursementListString);
		response.getWriter().write(reimbursementListString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
