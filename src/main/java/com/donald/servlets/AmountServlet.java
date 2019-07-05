package com.donald.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.donald.services.ReimbursementServiceImpl;
import com.donald.util.LoggingUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AmountServlet
 */
public class AmountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AmountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get projected amount

		String body = request.getReader().readLine();
		LoggingUtil.debug("cost and event update items ->" + body);

		// treating JSON obj as DOM tree
		JsonNode parent = new ObjectMapper().readTree(body);

		Integer cost = parent.get("cost").asInt();
		String eventType = parent.get("eventType").asText();

		LoggingUtil.debug("requestId: " + cost);
		LoggingUtil.debug("decision: " + eventType);

		// calling service method
		int projectedAmount = rsi.calculateAwardByReimbursementType(eventType, cost);

		String projectedAmountString = Integer.toString(projectedAmount);
		// return success message!
		response.getWriter().write(projectedAmountString);
	}

}
