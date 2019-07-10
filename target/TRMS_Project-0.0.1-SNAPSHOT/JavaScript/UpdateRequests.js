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

function updateRequest(event){
    event.preventDefault();

    let requestId = document.getElementById("request_id").value;
    let gradingFormat = document.getElementsByName("radioDecision");
    //get checked radio button
    for (let i = 0; i < gradingFormat.length; i++){
        if(gradingFormat[i].checked){
            gradingFormat = gradingFormat[i].value;
            break;
        }
    }

    let grade = document.getElementById("gradeInput").value;

    //need presentation implementation

    //make new JSON object
    let updateRequest = new UpdateRequest(requestId, gradingFormat, grade);

    let xhr = new XMLHttpRequest();

     
	xhr.onreadystatechange = function() {
        
        if (xhr.readyState === 4 && xhr.status === 200) {
            
            alert(xhr.responseText);
            getRequests();

		}
    }
    
    xhr.open("POST", "update_request", true);
    
    console.log(updateRequest);

    xhr.send(JSON.stringify(updateRequest));



}

let UpdateRequest = function (requestId, gradingFormat, grade){
    this.requestId = requestId;
    this.gradingFormat = gradingFormat;
    this.grade = grade;
}


function gradeCheck(){
    if (document.getElementById('gradeCheck').checked){
        document.getElementById('gradeRow').style.visibility = 'visible';
        //document.getElementById('gradeRow').style.display = 'inherit';
        document.getElementById('presentationRow').style.visibility = 'hidden';
    } else {
        document.getElementById('gradeRow').style.visibility = 'hidden';
        document.getElementById('presentationRow').style.visibility = 'visible';
    }
}

window.onload = function () {
    getRequests();
    gradeCheck();

  document.getElementById("updateForm").addEventListener("submit", updateRequest)
  document.getElementById("gradeCheck").addEventListener("click", gradeCheck);
  document.getElementById("presentationCheck").addEventListener("click", gradeCheck);
}