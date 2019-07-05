package com.donald.dao;

import java.util.List;

import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;

public interface ReimbursementDAOInt {

	public int insertReimbursement(Employee loggedInEmployee, ReimbursementRequest reimbursementRequest);

	public List<ReimbursementRequest> viewReimbursementRequestForEmployee(Employee loggedInEmployee);

	public List<ReimbursementRequest> viewReimbursementRequestsDeptHead(Employee loggedInEmployee);

	public List<ReimbursementRequest> viewReimbursementRequestsBenCo();

	public String getEmployeeUsernameById(int employeeId);

	public String getReimbursementTypeById(int reimbursementTypeId);

	public int updateRequestAdditionalInfo(int requestId, String additionalInfo);

	public int updateAcceptRequest(int requestId);

	public int updateDenyRequest(int requestId);

	public List<ReimbursementRequest> viewPersonalReimbursementRequests(Employee loggedInEmployee);

	public int updateGradeRequest(int requestId, String grade);

	public List<ReimbursementRequest> viewGradedRequests();

	public int updateFinalGrade(int requestId, boolean denied, boolean award_given);

	public int getEmployeeIdByRequestId(int requestId);

	public ReimbursementRequest getReimbursementRequest(int requestId);

	public int getReimbursementPaybackPercentageByReimbursementType(String ReimbursementRequestType);

	public int insertReimbursementAward(String employeeUsername, int reimbursementTypeId, int awardAmount);

	public int getEmployeeIdByUsername(String userName);

	public List<ReimbursementRequest> getAdditionalInformationRequests(Employee loggedInEmployee);

	public int updateAdditionalInformationResponse(int requestId, String responseMessage, Employee loggedInEmployee);

}
