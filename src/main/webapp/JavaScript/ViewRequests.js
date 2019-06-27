function getRequests(){
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 & xhr.status === 200){
            let requestArr = JSON.parse(xhr.responseText);
            appendToTable(requestArr);     
        }
    }

    xhr.open("GET", "view_requests", true);

    xhr.send();
}

// TODO fix
function appendToTable(requestArr){
    const tableBody = document.getElementById("main-table-body");

    for (let i = 0; i < requestArr.length; i++){
        tableBody.innerHTML += `
        <tr>
            <td>${requestArr[i].id}</td>
            <td>${requestArr[i].username}</td>
        </tr>
        `;
    }
    
}




window.onload = function () {
    getRequests();
}