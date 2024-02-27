<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	public boolean isError = false;
%>
<%
	if (request.getParameter("error") != null && request.getParameter("error").equals("1")) {
		isError = true;
	} else {
		isError = false;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SQL Injection Assignment</title>
<style><%@include file="/WEB-INF/css/styles.css"%></style>
<style>
	@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
</style>
</head>
<body>
	<div class="login-container">
		<form class="login-form-container" action="/SQL_Injection/EmployeeLogin" method="POST">
			<h1 class="login-heading">Employee.</h1>
			<label class="login-label">Name</label>
			<input class="login-input" type="text" name="name">
			<label class="login-label">Age</label>
			<input class="login-input login-password" type="number" name="age">
			<button class="login-btn" type="submit">Enter</button>
			<a class="login-link" href="http://localhost:8080/SQL_Injection/login">Customer? Log in here.</a>
		</form>
	</div>
</body>
</html>