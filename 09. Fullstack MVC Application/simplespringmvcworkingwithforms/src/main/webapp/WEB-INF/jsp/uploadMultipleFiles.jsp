<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload Multiple File</title>

	<form method="POST" action="uploadMultipleFiles" enctype="multipart/form-data">
		<p>choose 1st file <input type="file" name="file"></p>
		<p>1st file name : <input type="text" name="name"></p>
		
		<p>choose 2nd file <input type="file" name="file"></p>
		<p>2nd file name : <input type="text" name="name"></p>

		<p>choose 3th file <input type="file" name="file"></p>
		<p>3th file name : <input type="text" name="name"></p>	
		
		<input type="submit" value="upload">	
	</form>
</head>
<body>

</body>
</html>
