function returnMessage(){

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {

		if (xhr.readyState === 4 && xhr.status === 200) {
			alert(xhr.responseText);
		}
	}

    xhr.open("POST", "reimbursement_request_form", true);

    xhr.send();
}




window.onload = function() {
    document.getElementById("reimbursementForm").addEventListener("submit", returnMessage);
}
