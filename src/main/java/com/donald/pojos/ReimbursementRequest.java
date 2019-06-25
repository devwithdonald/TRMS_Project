package com.donald.pojos;

public class ReimbursementRequest extends Reimbursement{

	private int status; // level of approval

	public ReimbursementRequest(String dateOfEvent, String locationOfEvent, String description,
			int cost, String eventType, int status) {
		super(dateOfEvent, locationOfEvent, description, cost, eventType);
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
