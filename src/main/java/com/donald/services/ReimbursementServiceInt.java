package com.donald.services;

import java.util.List;

import com.donald.pojos.Employee;
import com.donald.pojos.Reimbursement;
import com.donald.pojos.ReimbursementRequest;

public interface ReimbursementServiceInt {

	public Reimbursement insertReimbursementRequest(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest);

	public int getEventTypeId(String eventType); 
	
	public String sendCorrectRedirectLink(Employee loggedInEmployee);
	
	//supervisor and change parameter (supervisor id???) just append to list?
	public List<ReimbursementRequest> viewPendingReimbursementRequests(Employee loggedInEmployee);
	
	
	public String reimbursementDecisionMaker(int requestId, String decision, String additonalInfo, Employee loggedInEmployee);
	
	public boolean requestAdditionalInfo(int requestId, String additonalInfo);
	
	public boolean acceptRequest(int requestId);
	
	public boolean denyRequest(int requestId);
	
	public boolean reimbursementIdVerification(Employee loggedInEmployee, int requestId);
	
	public List<ReimbursementRequest> viewPersonalPendingReimbursementRequests(Employee loggedInEmployee);
	
	public String updateReimbursementGrade(int requestId, String gradingFormat, String grade, Employee loggedInEmployee);
	
	public boolean updateGradeIdVerification(Employee loggedInEmployee, int requestId);
	
	public List<ReimbursementRequest> viewGradedRequests(Employee loggedInEmployee);
	
	public String finalGradeDecision(int requestId, String decision, Employee loggedInEmployee);
	
	public boolean finalGradeIdVerification(Employee loggedInEmployee, int requestId);
	
	public int calculateAward(int requestId);
//	
//	public boolean dateCheck(String date);
	
	public int calculateAwardByReimbursementType(String reimbursementType, int cost);
	
	public List<ReimbursementRequest> viewAdditionalInformationRequests (Employee loggedInEmployee);
	

}
