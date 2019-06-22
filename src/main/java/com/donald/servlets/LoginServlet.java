package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.donald.pojos.Employee;
import com.donald.services.EmployeeServiceImpl;

public class LoginServlet extends HttpServlet {
	
	private EmployeeServiceImpl esi = new EmployeeServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("HTML/login.html");
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
			System.out.println("failed login");
		} else {
			// successful login
			resp.getWriter().write("Successful Login!");
			System.out.println("success login");
		}
	}

}
