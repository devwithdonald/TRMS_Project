package com.donald.services;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;

public interface ReimbursementServiceInt {

	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, String date, String time, String location, String description, int cost, String event);

	public int getEventTypeId(String eventType); 
	
	public String sendCorrectRedirectLink(Employee loggedInEmployee);
}
