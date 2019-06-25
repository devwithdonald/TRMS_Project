package com.donald.pojos;

public class ReimbursementAward extends Reimbursement{
	
	private int awardAmount;



	public ReimbursementAward(String eventType, String dateOfEvent, String locationOfEvent, String timeOfEvent,
			String description, int cost, int awardAmount) {
		super(eventType, dateOfEvent, locationOfEvent, timeOfEvent, description, cost);
		this.awardAmount = awardAmount;
	}

	public int getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(int awardAmount) {
		this.awardAmount = awardAmount;
	}

	@Override
	public String toString() {
		return "ReimbursementAward [awardAmount=" + awardAmount + "]";
	}
	
	
	
	
	
}
