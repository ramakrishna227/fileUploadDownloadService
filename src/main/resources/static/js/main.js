'use strict';

var singleFileUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadSuccess = document
		.querySelector('#singleFileUploadSuccess');
var singleFileUploadFailure = document.querySelector('#singleFileUploadError');

var multiFileUploadForm = document.querySelector('#multiFileUploadForm');
var multiFileUploadInput = document.querySelector('#multiFileUploadInput');
var multiFileUploadSuccess = document.querySelector('#multiFileUploadSuccess');
var multiFileUploadFailure = document.querySelector('#multiFileUploadError')

function uploadSingleFile(file) {
	var formData = new FormData();
	formData.append("file", file);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/uploadFile");

	xhr.onload = function() {
		console.log(xhr.responseText);
		var response = JSON.parse(xhr.responseText);
		if (xhr.status == 200) {
			singleFileUploadFailure.style.display = "none";
			singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully</p> <p>DownloadURL : <a href='"
					+ response.fileDownloadURI
					+ "'target='_blank'>"
					+ response.fileDownloadURI + "</a></p>";
			singleFileUploadSuccess.style.display = "block";
		} else {
			singleFileUploadSuccess.style.display = "none";
			singleFileUploadFailure.innerHTML = (response && response.message)
					|| "Some Error Occurred";
		}
	}
	xhr.send(formData);
}

function uploadMultipleFiles(files) {
	var formData = new FormData();
	for (var index = 0; index < files.length; index++) {
		formData.append("files", files[index]);
	}
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/uploadMultipleFiles");

	xhr.onload = function() {
		console.log(xhr.responseText);
		var response = JSON.parse(xhr.responseText);
		if (xhr.status == 200) {
			multiFileUploadFailure.style.display = "none";
			var content = "<p>All files uploaded successfully</p>"
			for (var i = 0; i < response.length; i++) {
				content += "<p>Download URL: <a href='"
						+ response[i].fileDownloadURI + "'target='_blank'>"
						+ response[i].fileDownloadURI + "</a></p>";
			}
			multiFileUploadSuccess.innerHTML = content;
			multiFileUploadSuccess.style.display = "block";
		} else {
			multiFileUploadSuccess.style.display = "none";
			multiFileUploadFailure.innterHTML = (response && response.message)
					|| "Sone Error Occured";
		}

	}
	xhr.send(formData);
}

singleFileUploadForm.addEventListener('submit', function(event) {
	var files = singleFileUploadInput.files;
	if (files.length === 0) {
		singleFileUploadFailure.innerHTML = "Please select a file";
		singleFileUploadFailure.style.display = "block";
	}
	uploadSingleFile(files[0]);
	event.preventDefault();
}, true);

multiFileUploadForm.addEventListener('submit', function(event) {
	var files = multiFileUploadInput.files;
	if (files.length === 0) {
		multiFileUploadFailure.innerHTML = "Please select files";
		multiFileUploadFailure.style.display = "block";
	}
	uploadMultipleFiles(files);
	event.preventDefault();

}, true);