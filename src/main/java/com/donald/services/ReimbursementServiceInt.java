package com.donald.services;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;

public interface ReimbursementServiceInt {

	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, String date, String time, String location, String description, int cost, String event, String gradingFormat, String passingGrade);

	public int getEventTypeId(String eventType); 
	
	public String sendCorrectRedirectLink(Employee loggedInEmployee);
	
	public String viewPendingReimbursements(Employee loggedInEmployee);
}
