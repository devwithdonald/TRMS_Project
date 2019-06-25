package com.donald.pojos;

public class ReimbursementAward extends Reimbursement{
	
	private int awardAmount;

	public ReimbursementAward(int id, String type, String dateOfEvent, String locationOfEvent, String description,
			int cost, String eventType, int awardAmount) {
		super(id, type, dateOfEvent, locationOfEvent, description, cost, eventType);
		this.awardAmount = awardAmount;
	}

	public int getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(int awardAmount) {
		this.awardAmount = awardAmount;
	}
	
	
	
}
