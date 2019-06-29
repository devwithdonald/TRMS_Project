package com.donald.services;

import java.util.List;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;
import com.donald.pojos.ReimbursementRequest;

public interface ReimbursementServiceInt {

	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, String date, String time, String location, String description, int cost, String event, String gradingFormat, String passingGrade);

	public int getEventTypeId(String eventType); 
	
	public String sendCorrectRedirectLink(Employee loggedInEmployee);
	
	//supervisor and change parameter (supervisor id???) just append to list?
	public List<ReimbursementRequest> viewPendingReimbursementRequests(Employee loggedInEmployee);
	
	
	public String reimbursementDecisionMaker(int requestId, String decision, String additonalInfo, Employee loggedInEmployee);
	
	public boolean acceptRequest(int requestId);
	
	public boolean denyRequest(int requestId);
	
	public boolean reimbursementIdVerification(Employee loggedInEmployee, int requestId);
	
	//need method to request additional info
}
