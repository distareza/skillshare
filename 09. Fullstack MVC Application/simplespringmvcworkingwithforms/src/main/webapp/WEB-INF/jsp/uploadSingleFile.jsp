<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload file</title>
</head>
<body>

	<form method="POST" action="uploadSingleFile" enctype="multipart/form-data">
		<p>Choose File. <input type="file" name="file"></p>
		<p>File Name : <input type="text" name="name">
		<input type="submit" value="upload">
	</form>

</body>
</html>
