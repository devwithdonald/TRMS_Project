package com.donald.pojos;

public class ReimbursementRequest extends Reimbursement{

	private int status; // level of approval
	private String gradingFormat;
	private String passingGrade;
	
	
	
	public ReimbursementRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ReimbursementRequest(String eventType, String dateOfEvent, String locationOfEvent, String timeOfEvent,
			String description, int cost, int status, String gradingFormat, String passingGrade) {
		super(eventType, dateOfEvent, locationOfEvent, timeOfEvent, description, cost);
		this.status = status;
		this.gradingFormat = gradingFormat;
		this.passingGrade = passingGrade;
	}



	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public String getPassingGrade() {
		return passingGrade;
	}

	public void setPassingGrade(String passingGrade) {
		this.passingGrade = passingGrade;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReimbursementRequest [status=" + status + "]";
	}
	
	
	
	

	
}
