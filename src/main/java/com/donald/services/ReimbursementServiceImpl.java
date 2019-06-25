package com.donald.services;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;
import com.donald.util.LoggingUtil;

public class ReimbursementServiceImpl implements ReimbursementServiceInt{

	@Override
	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, String date, String time, String location, String description,
			int cost, String event) {
		LoggingUtil.trace("insertReimbursementRequest()");
		// TODO Auto-generated method stub
		
		Reimbursement reimbursementRequest = null;
		
		//call the DAO!	
		
		return reimbursementRequest ;
	}

}
