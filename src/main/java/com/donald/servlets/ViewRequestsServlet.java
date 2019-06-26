package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Associate;
import com.donald.pojos.Employee;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;

public class ViewRequestsServlet extends HttpServlet{
	
	ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sess = req.getSession(false);
		if (sess == null || sess.getAttribute("employee") == null) {
			req.getRequestDispatcher("login").forward(req, resp);
			// need to return so the rest of the method doesn't run
			LoggingUtil.warn("Tried to reach View Requests access by non logged in user. Successfully redirected.");
			return;
		} else {
			//send to appropriate site
			sess = req.getSession();
			Employee loggedInEmployee = (Employee) sess.getAttribute("employee");
			//need to send if associate
			if (loggedInEmployee instanceof Associate) {
				resp.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee));
			} else {
				resp.sendRedirect("view_requests.html");
			}
			
		}
		
		

	}
	
	
	// send acceptance or denial requests 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//populate the table!
	}
	
	
}
