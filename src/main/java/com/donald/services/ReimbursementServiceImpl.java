package com.donald.services;

import com.donald.dao.ReimbursementDAOImpl;
import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;
import com.donald.pojos.ReimbursementRequest;
import com.donald.util.LoggingUtil;

public class ReimbursementServiceImpl implements ReimbursementServiceInt {

	ReimbursementDAOImpl rdi = new ReimbursementDAOImpl();

	@Override
	public ReimbursementRequest insertReimbursementRequest(Employee loggedInEmployee, String date, String time,
			String location, String description, int cost, String eventType) {
		LoggingUtil.trace("insertReimbursementRequest()");
		
		ReimbursementRequest reimbursementRequest = null;

		// make new Reimbursement
		reimbursementRequest = new ReimbursementRequest(eventType, date, location, time, description, cost, 0);

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

	@Override
	public int getEventTypeId(String eventType) {
		
		int id = 0;
		
		switch(eventType) {
		case "University Course":
			id = 1;
			break;
		case "Seminar":
			id = 2;
			break;
		case "Certification Preparation Classes":
			id = 3;
			break;
		case "Certification":
			id = 4;
			break;
		case "Technical Training":
			id = 5;
			break;
		case "Other":
			id = 6;
			break;
		}
		
		return id;
	}

}
