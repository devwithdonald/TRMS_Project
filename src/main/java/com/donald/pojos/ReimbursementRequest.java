package com.donald.pojos;

public class ReimbursementRequest extends Reimbursement{

	private int status; // level of approval



	public ReimbursementRequest(String eventType, String dateOfEvent, String locationOfEvent, String timeOfEvent,
			String description, int cost, int status) {
		super(eventType, dateOfEvent, locationOfEvent, timeOfEvent, description, cost);
		this.status = status;
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
