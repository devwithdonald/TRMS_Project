package com.donald.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReimbursementRequestFormServlet extends HttpServlet{

	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.sendRedirect("reimbursement_request_form.html");
	}
}
