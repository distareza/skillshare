<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Summer Picnic</title>
</head>
<body>
	<h2>Fantabulous University Summer Picnic Program</h2>
	<a href="details/registration">Click here to register.</a>
	
	<h2>File Upload</h2>
	<br/>
	<a href="uploadForm">click to upload</a>
	<br/><br/>
	
	<h2>Upload Single File</h2><br/>
	<input type="button" onclick="location.href='uploadSingleFile'" 
	style="background-color: #008080; 
			color: black;
			width: 150px; height: 40px; top: 40px;
			"
	value="Single File Upload">
	<br/><br/>

	<h2>Upload Multiple File</h2><br/>
	<input type="button" onclick="location.href='uploadMultipleFiles'" 
	style="background-color: #008080; 
			color: black;
			width: 150px; height: 40px; top: 40px;
			"
	value="Multiple File Upload">
	<br/><br/>

	<h2>Upload Multiple Selection File</h2><br/>
	<input type="button" onclick="location.href='uploadMultipleSelectionForm'" 
	style="background-color: #008080; 
			color: black;
			width: 250px; height: 40px; top: 40px;
			"
	value="Multiple Selection File Upload">
	<br/><br/>
	
	<h2>File Download</h2><br/>
	<a href="download/downloadForm">Click To Download File</a>
	<br/><br/>	
	
</body>
</html>
