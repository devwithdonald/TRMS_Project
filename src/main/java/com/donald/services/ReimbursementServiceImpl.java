package com.donald.services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import com.donald.dao.EmployeeDAOImpl;
import com.donald.dao.ReimbursementDAOImpl;
import com.donald.pojos.Associate;
import com.donald.pojos.BenefitsCoordinator;
import com.donald.pojos.DepartmentHead;
import com.donald.pojos.Employee;
import com.donald.pojos.ReimbursementRequest;
import com.donald.pojos.Supervisor;
import com.donald.util.LoggingUtil;

public class ReimbursementServiceImpl implements ReimbursementServiceInt {

	ReimbursementDAOImpl rdi = new ReimbursementDAOImpl();
	EmployeeDAOImpl edi = new EmployeeDAOImpl();
	EmployeeServiceImpl esi = new EmployeeServiceImpl();

	@Override
	public ReimbursementRequest insertReimbursementRequest(Employee loggedInEmployee,
			ReimbursementRequest reimbursementRequest) {
		LoggingUtil.trace("insertReimbursementRequest()");

		int successCode = rdi.insertReimbursement(loggedInEmployee, reimbursementRequest);

		edi.updateEmployeePendingBalance(loggedInEmployee,
				calculateAwardByReimbursementType(reimbursementRequest.getEventType(), reimbursementRequest.getCost()));

		if (successCode == 0) {
			return null;
		} else {
			return reimbursementRequest;
		}

	}

	@Override
	public int getEventTypeId(String eventType) {

		int id = 0;

		switch (eventType) {
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

	@Override
	public String sendCorrectRedirectLink(Employee loggedInEmployee) {

		String link;

		if (loggedInEmployee instanceof Associate) {
			link = "reimbursement_request_form.html";
		} else {
			link = "request_form_non_associate.html";
		}

		return link;
	}

	@Override
	public List<ReimbursementRequest> viewPendingReimbursementRequests(Employee loggedInEmployee) {
		LoggingUtil.debug("viewPendingReimbursementRequests SERVICE");

		List<ReimbursementRequest> reimbursementRequestList = null;

		if (loggedInEmployee instanceof DepartmentHead) {
			reimbursementRequestList = rdi.viewReimbursementRequestsDeptHead(loggedInEmployee);
		} else if (loggedInEmployee instanceof Supervisor) {
			reimbursementRequestList = rdi.viewReimbursementRequestForEmployee(loggedInEmployee);
		} else if (loggedInEmployee instanceof BenefitsCoordinator) {
			reimbursementRequestList = rdi.viewReimbursementRequestsBenCo();
		}

		return reimbursementRequestList;
	}

	@Override
	public String reimbursementDecisionMaker(int requestId, String decision, String additionalInfo,
			Employee loggedInEmployee) {
		Boolean success = false;
		String message = null;

		boolean verifiedRequestId = reimbursementIdVerification(loggedInEmployee, requestId);

		if (verifiedRequestId == true) {

			if (decision.equals("Accept")) {
				success = acceptRequest(requestId);
				message = "accepted";
			} else if (decision.equals("Deny")) {
				success = denyRequest(requestId);

				esi.denyPendingReward(requestId);

				message = "denied";
			} else if (decision.equals("Request Additional Information")) {
				success = requestAdditionalInfo(requestId, additionalInfo);
				message = "response went through";

			}

			if (success == true) {
				return "Reimbursement request " + message + " successfully";
			} else {
				return "Reimbursement request " + message + " unsuccessfully";
			}
		} else {
			return "Invalid Request Id";
		}

	}

	@Override
	public boolean requestAdditionalInfo(int requestId, String additionalInfo) {

		int rowsAffected = rdi.updateRequestAdditionalInfo(requestId, additionalInfo);

		if (rowsAffected == 1) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean acceptRequest(int requestId) {

		int rowsAffected = rdi.updateAcceptRequest(requestId);

		if (rowsAffected == 1) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean denyRequest(int requestId) {

		int rowsAffected = rdi.updateDenyRequest(requestId);

		if (rowsAffected == 1) {

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean reimbursementIdVerification(Employee loggedInEmployee, int requestId) {

		List<ReimbursementRequest> reimbursementRequests = viewPendingReimbursementRequests(loggedInEmployee);

		for (int i = 0; i < reimbursementRequests.size(); i++) {
			if (reimbursementRequests.get(i).getId() == requestId) {
				return true;
			}
		}

		return false;

	}

	@Override
	public List<ReimbursementRequest> viewPersonalPendingReimbursementRequests(Employee loggedInEmployee) {

		List<ReimbursementRequest> reimbursementRequestList = rdi.viewPersonalReimbursementRequests(loggedInEmployee);

		return reimbursementRequestList;
	}

	@Override
	public String updateReimbursementGrade(int requestId, String gradingFormat, String grade,
			Employee loggedInEmployee) {

		int success = 0;

		boolean verifiedRequestId = updateGradeIdVerification(loggedInEmployee, requestId);

		if (verifiedRequestId == true) {

			if (gradingFormat.equals("Grade")) {
				success = rdi.updateGradeRequest(requestId, grade);
			} else if (gradingFormat.equals("Presentation")) {
				LoggingUtil.trace("Presentaion selected, in updateReimbursementGrade - needs implementation");
			}

			if (success == 1) {
				return "Reimbursement request updated successfully";
			} else {
				return "Reimbursement request updated unsuccessfully";
			}
		} else {
			return "Invalid Request Id";
		}
	}

	@Override
	public boolean updateGradeIdVerification(Employee loggedInEmployee, int requestId) {

		List<ReimbursementRequest> personalPendingReimbursementRequests = viewPersonalPendingReimbursementRequests(
				loggedInEmployee);

		for (int i = 0; i < personalPendingReimbursementRequests.size(); i++) {
			if (personalPendingReimbursementRequests.get(i).getId() == requestId) {
				return true;
			}
		}

		return false;

	}

	@Override
	public List<ReimbursementRequest> viewGradedRequests(Employee loggedInEmployee) {
		List<ReimbursementRequest> gradedRequestList = null;

		if (loggedInEmployee instanceof BenefitsCoordinator) {
			gradedRequestList = rdi.viewGradedRequests();
		}

		return gradedRequestList;
	}

	@Override
	public String finalGradeDecision(int requestId, String decision, Employee loggedInEmployee) {

		boolean verifiedRequestId = false;

		int numberOfRows = 0;

		String message = null;

		verifiedRequestId = finalGradeIdVerification(loggedInEmployee, requestId);

		if (verifiedRequestId == true) {

			if (decision.equals("Accept")) {

				numberOfRows = rdi.updateFinalGrade(requestId, false, true);

				ReimbursementRequest employeeRequest = rdi.getReimbursementRequest(requestId);

				int awardAmount = calculateAward(requestId);

				numberOfRows += rdi.insertReimbursementAward(employeeRequest.getUserName(),
						getEventTypeId(employeeRequest.getEventType()), awardAmount);

				esi.awardPendingReward(requestId, awardAmount);

				message = "accepted";
			} else if (decision.equals("Deny")) {

				numberOfRows = rdi.updateFinalGrade(requestId, true, false);
				esi.denyPendingReward(requestId);

				numberOfRows++;

				message = "denied";
			}

			if (numberOfRows == 2) {
				return "Reimbursement final grade " + message + " successfully";
			} else {
				return "Reimbursement final grade " + message + " unsuccessfully";
			}
		} else {
			return "Invalid Request Id";
		}

	}

	@Override
	public boolean finalGradeIdVerification(Employee loggedInEmployee, int requestId) {

		// use loggedInEmployee to get their list, check id reference then come back
		List<ReimbursementRequest> gradedRequestList = viewGradedRequests(loggedInEmployee);

		for (int i = 0; i < gradedRequestList.size(); i++) {
			if (gradedRequestList.get(i).getId() == requestId) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int calculateAward(int requestId) {

		// get the cost of the reimbursement by the request id
		ReimbursementRequest reimbursementRequest = rdi.getReimbursementRequest(requestId);

		int paybackPercentage = rdi
				.getReimbursementPaybackPercentageByReimbursementType(reimbursementRequest.getEventType());

		int awardAmount = (reimbursementRequest.getCost() * paybackPercentage) / 100;

		return awardAmount;
	}

	@Override
	public int calculateAwardByReimbursementType(String reimbursementType, int cost) {
		// select payback_percentage from reimbursement_type return that value (cast)
		int paybackPercentage = rdi.getReimbursementPaybackPercentageByReimbursementType(reimbursementType);

		int awardAmount = (cost * paybackPercentage) / 100;

		return awardAmount;
	}

	@Override
	public List<ReimbursementRequest> viewAdditionalInformationRequests(Employee loggedInEmployee) {
		List<ReimbursementRequest> additionalInfoRequestList = rdi.getAdditionalInformationRequests(loggedInEmployee);

		return additionalInfoRequestList;
	}

	@Override
	public String additionalInformationResponse(int requestId, String responseMessage, Employee loggedInEmployee) {
		int rowsAffected = rdi.updateAdditionalInformationResponse(requestId, responseMessage, loggedInEmployee);

		if (rowsAffected == 1) {

			return "Response was successful";
		} else {
			return "Response was unsuccessful";
		}
	}

}
