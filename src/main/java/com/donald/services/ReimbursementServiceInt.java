package com.donald.services;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;
import com.donald.pojos.ReimbursementRequest;

public interface ReimbursementServiceInt {

	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, String date, String time, String location, String description, int cost, String event, String gradingFormat, String passingGrade);

	public int getEventTypeId(String eventType); 
	
	public String sendCorrectRedirectLink(Employee loggedInEmployee);
	
	public ReimbursementRequest viewPendingReimbursementRequests(Employee loggedInEmployee);
}
