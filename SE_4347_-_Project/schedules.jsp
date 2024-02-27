<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
	<div class="schedules-container">
		<a class="btn top-right back-btn" href="javascript:window.history.back();">Go Back &larr;</a>
		<h1 class="home-heading">3D Printers</h1>

		<table class="table">
			<tr class="table-row table-head">
		    		<th>Printer #</th>
		    		<th>Filament Type</th>
		    		<th>Filament Amount (%)</th>
			        <th>Action</th>
	    	</tr>
			<c:forEach items="${printers}" var="printer">
		    	
		    	<tr class="table-row">
		    		<td><c:out value="${printer.pid}"/></td>
			        <td><c:out value="${printer.filament}"/></td>
			        <td><c:out value="${printer.filamentAmt*100}"/></td>
			        <% if (request.getParameter("role") != null &&request.getParameter("role").equals("0")) { %>
			        <td><a class="action-btn" href="http://localhost:8080/SQL_Injection/ViewSchedule/${printer.pid}?name=${name}&age=${age}&role=${role}">View Schedule</a></td>
			        <% } else { %>
      			    <td><a class="action-btn" href="http://localhost:8080/SQL_Injection/UpdateAmount/${printer.pid}?name=${name}&age=${age}&role=${role}">View Schedule</a></td>
			        <% } %>
		    	</tr>
			</c:forEach>
		</table>
		<% if (request.getParameter("role") != null &&request.getParameter("role").equals("1")) { %>
		<h2 class="home-heading reservation-heading">Above Average Price Filaments</h2>
		<table class="table">
			<tr class="table-row table-head">
	    		<th>Product</th>
	    		<th>Price</th>
	    	</tr>
			<c:forEach items="${filaments}" var="filament">
		    	<tr class="table-row">
		    		<td><c:out value="${filament.filamentType}"/></td>
			        <td><c:out value="${filament.filamentPrice}"/></td>
		    	</tr>
			</c:forEach>
		</table>
		<% } %>
	</div>
</body>
</html>