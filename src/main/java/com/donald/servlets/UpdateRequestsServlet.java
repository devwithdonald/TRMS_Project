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
 * Servlet implementation class UpdateRequestsServlet
 */
public class UpdateRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
	Employee loggedInEmployee;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateRequestsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoggingUtil.debug("in doGet(); for UpdateRequestServlet");

		HttpSession sess = request.getSession(false);

		loggedInEmployee = (Employee) sess.getAttribute("employee");

		List<ReimbursementRequest> reimbursementRequestList = rsi
				.viewPersonalPendingReimbursementRequests(loggedInEmployee);

		ObjectMapper om = new ObjectMapper();
		String reimbursementListString = om.writeValueAsString(reimbursementRequestList);
		LoggingUtil.info("Pulled requests JSON as String-> " + reimbursementListString);
		response.getWriter().write(reimbursementListString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// pulling information from the update form
		String body = request.getReader().readLine();
		LoggingUtil.debug("contents from update form ->" + body);

		// treating JSON obj as DOM tree
		JsonNode parent = new ObjectMapper().readTree(body);

		Integer requestId = parent.get("requestId").asInt();
		String gradingFormat = parent.get("gradingFormat").asText();
		String grade = parent.get("grade").asText();

		LoggingUtil.debug("requestId: " + requestId);
		LoggingUtil.debug("gradingFormat: " + gradingFormat);
		LoggingUtil.debug("grade: " + grade);

		// calling service method
		String message = rsi.updateReimbursementGrade(requestId, gradingFormat, grade, loggedInEmployee);
		// return success message!
		response.getWriter().write(message);
	}

}
