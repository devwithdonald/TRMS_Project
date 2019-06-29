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
	
	public String getEmployeeUsernameById(int employee_id);
	
	public String getReimbursementTypeById(int reimbursement_type_id);
	
	public int updateAcceptRequest(int request_id);
	
	public int updateDenyRequest(int request_id);
	
	public List<ReimbursementRequest> viewPersonalReimbursementRequests(Employee loggedInEmployee);
}
