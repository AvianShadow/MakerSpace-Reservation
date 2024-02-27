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
		<h1 class="home-heading">Printer Information</h1>
		<table class="table">
			<tr class="table-row table-head">
	    		<th>Printer #</th>
	    		<th>Filament Type</th>
	    		<th>Filament Amount (%)</th>
	    	</tr>	    	
	    	<tr class="table-row">
	    		<td><c:out value="${pid}"/></td>
		        <td><c:out value="${printer.filament}"/></td>
		        <td><c:out value="${printer.filamentAmt*100}"/></td>
	    	</tr>
		</table>
		<h2 class="home-heading reservation-heading">Suppliers</h2>
		<table class="table">
			<tr class="table-row table-head">
				<th>ID</th>
				<th>Product</th>
				<th>Price</th>
			</tr>
			<c:forEach items="${suppliers}" var="supplier">
		    	<tr class="table-row">
		    		<td><c:out value="${supplier.supId}"/></td>
		    		<td><c:out value="${supplier.product}"/></td>
			        <td><c:out value="${supplier.price}"/></td>
		    	</tr>
			</c:forEach>
		</table>
		<h2 class="reservation-heading">Update Printer #<c:out value="${pid}"/></h2>
		<form class="reserve-form" action="/SQL_Injection/UpdateAmount/${pid}" method="POST">
			<h3 class="reserve-label">Amount (%)</h3>
			<input type="number" min="0" max="100" name="amount">
			<input type="hidden" value='${pid}' name="pid">
			<input type="hidden" value='<%=request.getParameter("name")%>' name="name">
			<input type="hidden" value='<%=request.getParameter("age")%>' name="age">
			<input type="hidden" value='<%=request.getParameter("role")%>' name="role">
			
			<button class="btn reserve-btn" type="submit">Update</button>
		</form>
		<a class="btn top-right back-btn" href="javascript:window.history.back();">Go Back</a>
	</div>
</body>
</html>