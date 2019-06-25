package com.donald.dao;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;

public interface ReimbursementDAOInt {

	public int insertReimbursement(Employee loggedInEmployee, Reimbursement reimbursementRequest);
	
}
