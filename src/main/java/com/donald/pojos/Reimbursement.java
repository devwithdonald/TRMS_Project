package com.donald.pojos;

public abstract class Reimbursement {
	//private Event event; //? class of what grading format and stuff?? may not work and may just need string
	// if reimbursement.event.name == "" do this grading format?
	
	
	private int id; 
	private String type;
	private String dateOfEvent;
	private String locationOfEvent;
	private String description;
	private int Cost;
	private String eventType;
	
	public Reimbursement(int id, String type, String dateOfEvent, String locationOfEvent, String description, int cost,
			String eventType) {
		super();
		this.id = id;
		this.type = type;
		this.dateOfEvent = dateOfEvent;
		this.locationOfEvent = locationOfEvent;
		this.description = description;
		Cost = cost;
		this.eventType = eventType;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
		return Cost;
	}
	public void setCost(int cost) {
		Cost = cost;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "Reimbursment [id=" + id + ", type=" + type + ", dateOfEvent=" + dateOfEvent + ", locationOfEvent="
				+ locationOfEvent + ", description=" + description + ", Cost=" + Cost + ", eventType=" + eventType
				+ "]";
	}
	
	
	
	
 
}
