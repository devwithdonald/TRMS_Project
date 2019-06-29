package com.donald.dao;

import java.util.List;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;

public interface ReimbursementDAOInt {

	public int insertReimbursement(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest);
	
	//supervisor and change parameter (supervisor id???) just append to list?
	public List<ReimbursementRequest> viewReimbursementRequestForEmployee(Employee loggedInEmployee);
	
	public List<ReimbursementRequest> viewReimbursementRequestsDeptHead(Employee loggedInEmployee);

	public List<ReimbursementRequest> viewReimbursementRequestsBenCo();
	
	public String getEmployeeUsernameById(int employeeId);
	
	public String getReimbursementTypeById(int reimbursementTypeId);
	
	public int updateAcceptRequest(int requestId);
	
	public int updateDenyRequest(int requestId);
	
	public List<ReimbursementRequest> viewPersonalReimbursementRequests(Employee loggedInEmployee);
	
	public int updateGradeRequest(int requestId, String grade);
	
	public List<ReimbursementRequest> viewGradedRequests();
	
	public int finalGradeDecision(int requestId, String decision);
	
}
