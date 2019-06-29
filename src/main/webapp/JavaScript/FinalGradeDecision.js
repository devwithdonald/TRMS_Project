function getRequests(){
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 & xhr.status === 200){
            let requestArr = JSON.parse(xhr.responseText);
            appendToTable(requestArr);     
        }
    }

    xhr.open("GET", "grade_approval", true);

    xhr.send();
}


function appendToTable(requestArr){
    const tableBody = document.getElementById('grade-view-table-body');

    //clear contents first!
    tableBody.innerHTML = "";

    for (let i = 0; i < requestArr.length; i++){
        tableBody.innerHTML += `
        <tr>
            <td>${requestArr[i].id}</td>
            <td>${requestArr[i].userName}</td>
            <td>${requestArr[i].eventType}</td>
            <td>${requestArr[i].gradingFormat}</td>
            <td>${requestArr[i].passingGrade}</td>    
            <td>${requestArr[i].gradeReceived}</td>          
        </tr>
        `;
    }
    
}


function finalDecision(event){
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

    //make new JSON object
    let finalDecision = new FinalDecision(requestId, decision);

    let xhr = new XMLHttpRequest();

     
	xhr.onreadystatechange = function() {
        
        if (xhr.readyState === 4 && xhr.status === 200) {
            
            alert(xhr.responseText);
            getRequests();

		}
    }
    
    xhr.open("POST", "grade_approval", true);

    console.log(finalDecision);

    xhr.send(JSON.stringify(finalDecision));



}

let FinalDecision = function (requestId, decision){
    this.requestId = requestId;
    this.decision = decision;
}


window.onload = function () {
    getRequests();

    document.getElementById("finalDecisionForm").addEventListener("submit", finalDecision)
}