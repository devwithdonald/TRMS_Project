package com.donald.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class gradeRequestsApprovalServlet
 */
public class gradeRequestsApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	Employee loggedInEmployee;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public gradeRequestsApprovalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO login verification
		HttpSession sess = request.getSession(false);
		loggedInEmployee = (Employee) sess.getAttribute("employee");

		//should check instance of 
		List<ReimbursementRequest> gradedRequestList = rsi.viewGradedRequests(loggedInEmployee);

		ObjectMapper om = new ObjectMapper();
		String gradedRequestListString = om.writeValueAsString(gradedRequestList);
		LoggingUtil.info("Pulled requests JSON as String-> " + gradedRequestListString);
		response.getWriter().write(gradedRequestListString);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//need login verification 
		
		// pulling information from the update form
		String body = request.getReader().readLine();
		LoggingUtil.debug("contents from update form ->" + body);
		
		// treating JSON obj as DOM tree
		JsonNode parent = new ObjectMapper().readTree(body);
		
		Integer requestId = parent.get("requestId").asInt();
		String decision = parent.get("decision").asText();
		
		LoggingUtil.debug("requestId: " + requestId);
		LoggingUtil.debug("decision: " + decision);
		

		//calling service method 
		String message = rsi.finalGradeDecision(requestId, decision, loggedInEmployee);
		//return success message!
		response.getWriter().write(message);
	}

}
