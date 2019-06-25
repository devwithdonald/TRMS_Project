package com.donald.services;

import com.donald.dao.ReimbursementDAOImpl;
import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;
import com.donald.pojos.ReimbursementRequest;
import com.donald.util.LoggingUtil;

public class ReimbursementServiceImpl implements ReimbursementServiceInt {

	ReimbursementDAOImpl rdi = new ReimbursementDAOImpl();

	@Override
	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, String date, String time,
			String location, String description, int cost, String eventType) {
		LoggingUtil.trace("insertReimbursementRequest()");
		// TODO Auto-generated method stub

		Reimbursement reimbursementRequest = null;

		// make new Reimbursement
		reimbursementRequest = new ReimbursementRequest(date, location, description, cost, eventType, 0);

		// call the DAO!
		int successCode = rdi.insertReimbursement(loggedInEmployee, reimbursementRequest);

		// if DAO returns 0 then make reimbursement null, else return the request
		// else the id
		if (successCode == 0) {
			return null;
		} else {
			return reimbursementRequest;
		}

	}

}
