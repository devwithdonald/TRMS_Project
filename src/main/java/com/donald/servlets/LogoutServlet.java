package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Employee;
import com.donald.util.LoggingUtil;

public class LogoutServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		//TODO FIX
		HttpSession sess = req.getSession();
		Employee loggedInEmployee = (Employee) sess.getAttribute("employee");
		LoggingUtil.debug(loggedInEmployee.getUsername() + " is logging out");
		req.getSession().invalidate();
		
		//HttpSession sess = req.getSession(false);
		
		//if (sess != null) {
			//sess.invalidate();
			//sess = null;
		//}
		
		//resp.sendRedirect("/HTML/login");

		resp.sendRedirect(req.getContextPath()+"/HTML/login");
	}
	
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//
//
//		resp.sendRedirect("/login");
//	}

}
