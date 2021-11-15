<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Success</title>
</head>
<body>
	<div id="global">
		<h3>Following files are uploaded successfully</h3>
		<ol>
			<c:forEach items="${upload.images}" var="image">
				<li>${image.originalFilename}</li>
			</c:forEach>
		</ol>
	</div>
</body>
</html>
