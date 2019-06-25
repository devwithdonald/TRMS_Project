package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.donald.pojos.Employee;
import com.donald.services.EmployeeServiceImpl;
import com.donald.util.LoggingUtil;

public class LoginServlet extends HttpServlet {
	
	private EmployeeServiceImpl esi = new EmployeeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("login.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//login the employee
		Employee employee = esi.loginEmployee(username, password);
		
		if (employee == null) {
			//send response if failed login
			resp.setStatus(401);
			resp.getWriter().write("Failed Login");
			LoggingUtil.debug("Failed Login");

		} else {
			// successful login
			// will need to call methods that send the employee to their correct page
			// depending on who they are!
			resp.getWriter().write("Successful Login!");
			
			LoggingUtil.debug("Succesful Login");
		}
	}

}
