function getRequests(){
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 & xhr.status === 200){
            let requestArr = JSON.parse(xhr.responseText);
            console.log(requestArr);
            console.log('hello');
            appendToTable(requestArr);     
        }
    }

    xhr.open("GET", "view_requests", true);

    xhr.send();
}

// TODO fix
function appendToTable(requestArr){
    const tableBody = document.getElementById('requests-view-table-body');

    for (let i = 0; i < requestArr.length; i++){
        tableBody.innerHTML += `
        <tr>
            <td>${requestArr[i].id}</td>
            <td>${requestArr[i].userName}</td>
            <td>${requestArr[i].eventType}</td>
            <td>${requestArr[i].cost}</td>
            <td>${requestArr[i].locationOfEvent}</td>
            <td>${requestArr[i].dateOfEvent}</td>
            <td>${requestArr[i].description}</td>
            <td>${requestArr[i].gradingFormat}</td>
            <td>${requestArr[i].passingGrade}</td>            
        </tr>
        `;
    }
    
}




window.onload = function () {
    getRequests();
}