function getRequests(){
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 & xhr.status === 200){
            let requestArr = JSON.parse(xhr.responseText);
            appendToTable(requestArr);     
        }
    }

    xhr.open("GET", "update_request", true);

    xhr.send();
}

function appendToTable(requestArr){
    const tableBody = document.getElementById('update-view-table-body');

    //clear contents first!
    tableBody.innerHTML = "";

    for (let i = 0; i < requestArr.length; i++){
        tableBody.innerHTML += `
        <tr>
            <td>${requestArr[i].id}</td>
            <td>${requestArr[i].eventType}</td>
            <td>${requestArr[i].dateOfEvent}</td>
            <td>${requestArr[i].description}</td>
            <td>${requestArr[i].gradingFormat}</td>
            <td>${requestArr[i].passingGrade}</td>            
        </tr>
        `;
    }
    
}

function requestDecision(event){
    event.preventDefault();

    let requestId = document.getElementById("request_id").value;
    let decision = document.getElementsByName("radioDecision");
    //get checked radio button
    for (let i = 0; i < decision.length; i++){
        if(decision[i].checked){
            decision = decision[i].value;
            break;
        }
    }

    let additionalInfo = document.getElementById("additionalinfo").value;

    //make new JSON object
    let requestDecision = new RequestDecision(requestId, decision, additionalInfo);

    let xhr = new XMLHttpRequest();

     
	xhr.onreadystatechange = function() {
        
        if (xhr.readyState === 4 && xhr.status === 200) {
            
            alert(xhr.responseText);
            getRequests();

		}
    }
    
    xhr.open("POST", "view_requests", true);

    console.log(requestDecision);

    xhr.send(JSON.stringify(requestDecision));



}

let RequestDecision = function (requestId, decision, additionalInfo){
    this.requestId = requestId;
    this.decision = decision;
    this.additionalInfo = additionalInfo;
}

window.onload = function () {
    getRequests();

  document.getElementById("updateForm").addEventListener("submit", updateRequest)
}