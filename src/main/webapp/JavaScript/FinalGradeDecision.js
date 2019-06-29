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

window.onload = function () {
    getRequests();

    //document.getElementById("approvalForm").addEventListener("submit", requestDecision)
}