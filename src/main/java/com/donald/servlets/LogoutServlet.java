package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession sess = req.getSession(false);
		
		if (sess != null) {
			sess.invalidate();
			sess = null;
		}
		
		

		resp.sendRedirect(req.getContextPath()+"/HTML/login");
	}
	
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//
//
//		resp.sendRedirect("/login");
//	}

}
