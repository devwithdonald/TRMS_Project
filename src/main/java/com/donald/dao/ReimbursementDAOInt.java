package com.donald.dao;

import java.util.List;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;

public interface ReimbursementDAOInt {

	public int insertReimbursement(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest);
	
	public List<ReimbursementRequest> viewReimbursementRequestForEmployee(Employee loggedInEmployee);

	public String getEmployeeUsernameById(int id);
}
