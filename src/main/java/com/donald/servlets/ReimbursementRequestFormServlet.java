package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.donald.pojos.Associate;
import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;

public class ReimbursementRequestFormServlet extends HttpServlet{
	
	//need Reimbursement service right here
	ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();

	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		LoggingUtil.trace("Redirect: reimbursement request -> .html");
		resp.sendRedirect("reimbursement_request_form.html");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		LoggingUtil.trace("in doPost(); for ReimbursementRequestFormServlet");
		
		//TODO need to get employee thats logged in!!!!!
		
		
		//FAKE THIS FOR NOW
		//Employee loggedInEmployee = 
		Employee loggedInEmployee = new Associate(1,"jack","hill","Associate");
				

		String date = req.getParameter("date");
		String time = req.getParameter("time");
		String location = req.getParameter("location");
		String description = req.getParameter("description");
		int cost = Integer.parseInt(req.getParameter("cost"));
		String eventType = req.getParameter("eventType");
		
		LoggingUtil.debug(date);
		LoggingUtil.debug(time);

		
		ReimbursementRequest reimbursementRequest = rsi.insertReimbursementRequest(loggedInEmployee, date, time, location, description, cost, eventType);
	
		
		//if null is sent back send back error
		if (reimbursementRequest == null) {
			//send response if failed login
			
			//resp.setStatus(500);
			resp.getWriter().write("Failed to insert reimbursement request");
			LoggingUtil.debug("Failed to insert reimbursement request");
		} else {
			resp.getWriter().write("Reimbursement request succesful!");
			LoggingUtil.debug("Reimbursement request succesful");
		}
		//else return them a confirmation? (NOT ID)
	}
}
