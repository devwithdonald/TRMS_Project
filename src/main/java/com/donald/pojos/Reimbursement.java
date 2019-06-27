package com.donald.pojos;

public abstract class Reimbursement {
	//private Event event; //? class of what grading format and stuff?? may not work and may just need string
	// if reimbursement.event.name == "" do this grading format?
	
	
	private int id; 
	private String eventType;
	private String dateOfEvent;
	private String locationOfEvent;
	private String timeOfEvent;
	private String description;
	private int cost;
	
	
	

	
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Reimbursement(String eventType, String dateOfEvent, String locationOfEvent, String timeOfEvent,
			String description, int cost) {
		super();
		this.eventType = eventType;
		this.dateOfEvent = dateOfEvent;
		this.locationOfEvent = locationOfEvent;
		this.timeOfEvent = timeOfEvent;
		this.description = description;
		this.cost = cost;
	}
	
	
	public String getTimeOfEvent() {
		return timeOfEvent;
	}


	public void setTimeOfEvent(String timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateOfEvent() {
		return dateOfEvent;
	}
	public void setDateOfEvent(String dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}
	public String getLocationOfEvent() {
		return locationOfEvent;
	}
	public void setLocationOfEvent(String locationOfEvent) {
		this.locationOfEvent = locationOfEvent;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", eventType=" + eventType + ", dateOfEvent=" + dateOfEvent
				+ ", locationOfEvent=" + locationOfEvent + ", timeOfEvent=" + timeOfEvent + ", description="
				+ description + ", Cost=" + cost + "]";
	}



	
	
	
	
 
}
