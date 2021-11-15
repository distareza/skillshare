<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Multiple Selection Form</title>
</head>
<body>
	<h2>Multiple File Upload</h2>
	<form:form modelAttribute="upload" action="save-upload" method="post" enctype="multipart/form-data">
		<table>
			<tr><td>Please Select one or more files to upload</td></tr>
			<tr><td>Enter name:</td></tr>
			<tr><td><input type="file" name="images" multiple="multiple" /></td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td><input type="submit" id="submit" value="Upload" 
				style="background-color: #DE1515; color: white; width: 100px; height: 40px; top: 250px;" /></td>
				<td><input type="reset" id="reset" value="Reset" 
				style="background-color: #DE1515; color: white; width: 100px; height: 40px; top: 250px;" /></td>
		</table>
	</form:form>
</body>
</html>
