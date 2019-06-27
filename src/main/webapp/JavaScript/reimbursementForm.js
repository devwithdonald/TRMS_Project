function returnMessage(){
	
	let dateOfEvent = document.getElementById("date").value;
	let timeOfEvent = document.getElementById("time").value;
	let locationOfEvent = document.getElementById("location").value;
	let description = document.getElementById("description").value;
	let cost = document.getElementById("costOfEvent").value;
	let eventType = document.getElementById("inputEvent").value;
	let gradingFormat = document.getElementById("inputGradingFormat").value;
	let passingGrade = document.getElementById("passingGradeInput").value;

	
	let reimbursementRequest = new ReimbursementRequest(dateOfEvent, timeOfEvent, locationOfEvent, description, cost, eventType, gradingFormat, passingGrade)

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {

		if (xhr.readyState === 4 && xhr.status === 200) {
			alert(xhr.responseText);
		}
	}

    xhr.open("POST", "reimbursement_request_form", true);

    xhr.send(JSON.stringify(reimbursementRequest));
}

let ReimbursementRequest = function (dateOfEvent, timeOfEvent, locationOfEvent, description, cost, eventType, gradingFormat, passingGrade) {
	this.dateOfEvent = dateOfEvent;
	this.timeOfEvent = timeOfEvent;
	this.locationOfEvent = locationOfEvent;
	this.description = description;
	this.cost = cost;
	this.eventType = eventType;
	this.gradingFormat = gradingFormat;
	this.passingGrade = passingGrade;
}




window.onload = function() {
    document.getElementById("reimbursementForm").addEventListener("submit", returnMessage);
}
