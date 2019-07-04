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
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AdditionalInfoServlet
 */
public class AdditionalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Employee loggedInEmployee;
	private static ReimbursementServiceImpl rsi = new ReimbursementServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdditionalInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get requests that need additional for the employee
		HttpSession sess = request.getSession(false);
		
		loggedInEmployee = (Employee) sess.getAttribute("employee");
		
		List<ReimbursementRequest> additionalInfoRequestList = rsi.viewAdditionalInformationRequests(loggedInEmployee);
		
		ObjectMapper om = new ObjectMapper();
		String reimbursementListString = om.writeValueAsString(additionalInfoRequestList);
		LoggingUtil.info("Pulled requests JSON as String-> " + additionalInfoRequestList);
		response.getWriter().write(reimbursementListString);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
