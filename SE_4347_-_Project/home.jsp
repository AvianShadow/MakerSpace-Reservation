<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	public String name = "";
	public String age = "";
	public String role = "";
%>    
<%
	name = request.getParameter("name");
	age = request.getParameter("age");
	role = request.getParameter("role");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SQL Injection Assignment</title>
<style><%@include file="/WEB-INF/css/styles.css"%></style>
<style><%@include file="/WEB-INF/css/home.css"%></style>
<style>
	@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
</style>
</head>
<body>
	<div class="topnav">
		<% if (request.getParameter("role") != null &&request.getParameter("role").equals("0")) { %>
		<a class="active" href="">Customer</a>
		<% } else { %>
		<a class="active" href="http://localhost:8080/SQL_Injection/EmployeeHome?name=<%=request.getParameter("name")%>&age=<%=request.getParameter("age")%>&role=<%=request.getParameter("role")%>">Employee</a>
		<% } %>
		<a  href="http://localhost:8080/SQL_Injection/login">Logout</a>
	</div>
	<div class="home-container">
		<% if (request.getParameter("reserve") != null &&request.getParameter("reserve").equals("1")) { %>
			<h1 class="home-heading">Your reservation was successful!</h1>
		<% } else if (request.getParameter("unauthorized") != null &&request.getParameter("unauthorized").equals("1")){ %>
			<h1 class="home-heading">The page you are trying to access is only authorized for employees!</h1>
		<% } else if (request.getParameter("update") != null &&request.getParameter("update").equals("1")){ %>
			<h1 class="home-heading">Your update was successful!</h1>
		<% } else { %>
			<h1 class="home-heading">Welcome, <%= name %>!</h1>
		<% } %>
		<form action="/SQL_Injection/Home" method="POST">
			<input type="hidden" value='<%=request.getParameter("name")%>' name="name" />
			<input type="hidden" value='<%=request.getParameter("age")%>' name="age" />
			<input type="hidden" value='<%=request.getParameter("role")%>' name="role" />
			<button class="btn" type="submit">View Printers</button>
		</form>
	</div>
</body>
</html>