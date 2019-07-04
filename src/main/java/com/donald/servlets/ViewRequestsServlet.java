package com.donald.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Associate;
import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ViewRequestsServlet extends HttpServlet{
	
	private static ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoggingUtil.debug("In ViewRequests doGet() SERVLET");
		Employee loggedInEmployee;
		HttpSession sess = req.getSession(false);
		if (sess == null || sess.getAttribute("employee") == null) {
			req.getRequestDispatcher("login").forward(req, resp);
			// need to return so the rest of the method doesn't run
			LoggingUtil.warn("Tried to reach View Requests access by non logged in user. Successfully redirected.");
			return;
		} else {
			//send to appropriate site
			sess = req.getSession();
			loggedInEmployee = (Employee) sess.getAttribute("employee");
			//need to send if associate
			if (loggedInEmployee instanceof Associate) {
				resp.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee));
				return;
			} 
		}
		

		
		//getting reimbursements
		//TODO may need to do switch case statements depending on whos asking!
		List<ReimbursementRequest> reimbursementRequestList = rsi.viewPendingReimbursementRequests(loggedInEmployee);
		
		ObjectMapper om = new ObjectMapper();
		String reimbursementListString = om.writeValueAsString(reimbursementRequestList);
		LoggingUtil.info("Pulled requests JSON as String-> " + reimbursementListString);
		resp.getWriter().write(reimbursementListString);
		
		//resp.sendRedirect("view_requests.html");
		

	}
	
	
	// send acceptance or denial requests 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoggingUtil.debug("In ViewRequests doPOST() SERVLET");
		
		Employee loggedInEmployee;
		HttpSession sess = req.getSession(false);
		if (sess == null || sess.getAttribute("employee") == null) {
			req.getRequestDispatcher("login").forward(req, resp);
			// need to return so the rest of the method doesn't run
			LoggingUtil.warn("Tried to reach View Requests access by non logged in user. Successfully redirected.");
			return;
		} else {
			//send to appropriate site
			sess = req.getSession();
			loggedInEmployee = (Employee) sess.getAttribute("employee");
			//need to send if associate
			if (loggedInEmployee instanceof Associate) {
				resp.sendRedirect(rsi.sendCorrectRedirectLink(loggedInEmployee));
				return;
			} 
		}
		
		
		
		// pulling information from the accept/deny form
		String body = req.getReader().readLine();
		LoggingUtil.debug("contents from approval form ->" + body);
		
		// treating JSON obj as DOM tree
		JsonNode parent = new ObjectMapper().readTree(body);
		
		Integer requestId = parent.get("requestId").asInt();
		String decision = parent.get("decision").asText();
		String additionalInfo = parent.get("additionalInfo").asText();
		
		LoggingUtil.debug("requestId: " + requestId);
		LoggingUtil.debug("decision: " + decision);
		LoggingUtil.debug("additionalInfo: " + additionalInfo);
		
		//call service to choose which one to call!
		// this should split off into different decision based off accept,deny or request additional info
		String message = rsi.reimbursementDecisionMaker(requestId, decision, additionalInfo, loggedInEmployee);
		//return success message!
		resp.getWriter().write(message);
		
		
		
		
	}
	
	
}
