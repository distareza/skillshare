<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Summer Picnic Registration</title>
<style type="text/css">
	.error {
		color: red;
		font-weight: bold;
	}
</style>
</head>
<body>

	<h2>Fantabulous picnic registration form</h2>
	<form:form action="submission" modelAttribute="registration">
		<b>Name</b><form:input path="name" /><form:errors path="name" cssClass="error" /><br/><br/>
		<b>Email</b><form:input path="email" /><form:errors path="email" cssClass="error" /><br/><br/>
		<b>Number of Guests : </b><form:input path="numGuests" /><form:errors path="numGuests" cssClass="error" /><br/><br/>
		<b>Gender</b><br/>
			<form:radiobutton path="gender" value="Male"/>Male<br/>
			<form:radiobutton path="gender" value="Female"/>Female<br/>
		<br/></br/>
		<b>Department</b>
			<form:select path="department" items="${departmentList}" />
			<br/><br/>
		<b>Meals</b><br/>
			<form:checkbox path="food" value="Breakfast"/>BreakFast<br/>
			<form:checkbox path="food" value="Lunch"/>Lunch<br/>
			<form:checkbox path="food" value="Dinner"/>Dinner<br/>
		<br/><br/>
		
		<input type="submit" value="Submit"/>
				
	</form:form>
</body>
</html>
