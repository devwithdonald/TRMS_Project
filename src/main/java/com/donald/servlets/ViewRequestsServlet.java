package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewRequestsServlet extends HttpServlet{
	
	protected void doGet(HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException{
		resp.sendRedirect("view_requests.html");
	}
}
