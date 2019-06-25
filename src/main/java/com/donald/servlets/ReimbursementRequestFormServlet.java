package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.donald.pojos.ReimbursementRequest;

public class ReimbursementRequestFormServlet extends HttpServlet{
	
	//need Reimbursement service right here
	ReimbursementServiceImpl rsi = new ReimbursementSeviceImpl();

	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.sendRedirect("reimbursement_request_form.html");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//TODO need to get employee thats logged in!!!!!
		
		//FAKE THIS FOR NOW
		//Employee loggedInEmployee = 

		String date = req.getParameter("date");
		String time = req.getParameter("time");
		String location = req.getParameter("location");
		String description = req.getParameter("description");
		int cost = Integer.parseInt(req.getParameter("cost"));
		String event = req.getParameter("event");
		
		

		
		ReimbursementRequest reimbursementRequest = rsi.insertReimbursementRequest(loggedInEmployee, date, time, location, description, cost, event);
	
		//if null is sent back send back error
	}
}
