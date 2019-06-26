package com.donald.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Employee;
import com.donald.services.EmployeeServiceImpl;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;

public class LoginServlet extends HttpServlet {
	
	private EmployeeServiceImpl esi = new EmployeeServiceImpl();
	private ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//check if logged in
		resp.sendRedirect("login.html");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//login the employee
		Employee loggedInEmployee = esi.loginEmployee(username, password);
		
		if (loggedInEmployee == null) {
			//send response if failed login
			resp.setStatus(401);
			LoggingUtil.debug("Failed Login");
			RequestDispatcher rd = req.getRequestDispatcher("failed_login.html");  
		    rd.include(req, resp);  

		} else {
			// successful login
			// will need to call methods that send the employee to their correct page
			// depending on who they are!
			//resp.getWriter().write("Successful Login!");
			LoggingUtil.debug("Succesful Login");
			HttpSession sess = req.getSession(true);
			sess.setAttribute("employee", loggedInEmployee);
	
			resp.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee));

		}
	}

}
