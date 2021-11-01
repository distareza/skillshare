<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Fantabulous University - Students</title>
	<style type="text/css">
		table, th, td {
			border: 2px solid green;
			border-collapse: collapse;
			padding: 8px;
			color: red;
		}
	</style>

</head>
<body>
	<h2>Students of Fantabulous Univeristy</h2>
	
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Major</th>
		</tr>
<c:forEach items="${students}" var="student">
		<tr>
			<td><a href="../students/${student.firstName}">${student.firstName}</a></td>
			<td>${student.lastName}</td>
			<td>${student.major}</td>
		</tr>
</c:forEach>
	</table>
		
</body>
</html>
