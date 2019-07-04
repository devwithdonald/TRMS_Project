package com.donald.services;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;

public interface ValidationServiceInt {

	public boolean balanceVerification(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest); 
	
	public boolean dateCheck(String date);
}
