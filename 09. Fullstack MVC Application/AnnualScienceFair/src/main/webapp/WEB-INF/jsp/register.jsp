<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="static_files/css/bootstrap.min.css">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>
        <link rel="stylesheet" href="static_files/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="static_files/css/main.css">

        <script src="static_files/js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
	
		<style>
			.error {
				color: red;
				font-style: italic;
				align: right;
			}
		</style>
</head>
<h1 style="text-align: center">Fantabulous science fair registration</h1>
<body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
        
          <button type="button" class="navbar-toggle collapsed" 
          	data-toggle="collapse" data-target="#navbar" aria-expanded="false" 
          	aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/AnnualScienceFair/">Annual Science Fair</a>
        </div>
        
      </div>
    </nav>

    <div class="jumbotron">
      <div class="container">

	<form:form id="regForm" modelAttribute="user" action="performRegistration" method="POST">
		<table class="center">
			<tr>
				<td><form:label path="studentId">Student ID</form:label></td>
				<td><form:input path="studentId" name="studentId" id="studentId" /></td>
				<td><form:errors path="studentId" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="studentName">Student Name</form:label></td>
				<td><form:input path="studentName" name="studentName" id="stundentName"/></td>
				<td><form:errors path="studentName" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="universityName">University Name</form:label></td>
				<td><form:input path="universityName" name="studentName" id="universityName"/></td>
				<td><form:errors path="universityName" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="projectArea">Project area</form:label></td>
				<td><form:select path="projectArea">
						<form:option value="" selected="selected" disabled="disabled">Project Area</form:option>
						<form:option value="Physics" label="Physics" />
						<form:option value="Chemistry" label="Chemistry" />
						<form:option value="Biology" label="Biology" />
					</form:select>
				</td>
				<td><form:errors path="projectArea" cssClass="error"/></td>
			</tr>
			<tr>
				<td><form:label path="email">Email</form:label></td>
				<td><form:input path="email" name="email" id="email"/></td>
				<td><form:errors path="email" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="password">Password</form:label></td>
				<td><form:input path="password" name="password" id="password"/></td>
				<td><form:errors path="password" cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td><form:button id="register" name="performRegistration">Register</form:button></td>
			</tr>
			<tr>
				<td></td>
				<td>Already have an account? <a href="performLogin">Login</a></td>
			<tr/>
		</table>	
	</form:form>

	</div>
	</div>

</body>
</html>