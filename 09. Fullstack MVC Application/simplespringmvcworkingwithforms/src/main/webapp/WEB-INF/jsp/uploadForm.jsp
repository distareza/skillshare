<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>File Upload Page</title>
</head>
<body>

	<h3 style="color:green">${filesuccess}</h3>
	<form:form method="post" action="saveFile" enctype="multipart/form-data">
		<p><label for="image">Choose file to upload</label></p>
		<p><input name="file" id="fileToUpload" type="file" /></p>
		<p><input type="submit" value="Upload" /></p>
	</form:form>

</body>
</html>
