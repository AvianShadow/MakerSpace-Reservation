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
		<h2 class="home-heading reservation-heading">Reservations</h2>
		<table class="table">
			<tr class="table-row table-head">
				<th>Name</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Start Time</th>
				<th>End Time</th>
			</tr>
			<c:forEach items="${schedules}" var="schedule">
		    	<tr class="table-row">
		    		<td><c:out value="${schedule.name}"/></td>
		    		<td><c:out value="${schedule.startDate}"/></td>
			        <td><c:out value="${schedule.endDate}"/></td>
			        <td><c:out value="${schedule.startTime}"/></td>
   			        <td><c:out value="${schedule.endTime}"/></td>
		    	</tr>
			</c:forEach>
		</table>
		<h2 class="reservation-heading">Schedule Printer #<c:out value="${pid}"/></h2>
		<form class="reserve-form" action="/SQL_Injection/ViewSchedule/${pid}" method="POST">
			<h3 class="reserve-label">Start Date (M/D/Y)</h3>
			<div>
			<input class="reserve-input" type="number" min="1" max="12" name="StartDateMonth">
			<span>/</span>
			<input class="reserve-input" type="number" min="1" max="31" name="StartDateDay">
			<span>/</span>
			<input class="reserve-input" type="number" min="2022" max="2023" name="StartDateYear">
			</div>
			
			<h3 class="reserve-label">End Date (M/D/Y)</h3>
			<div>
			<input class="reserve-input" type="number" min="1" max="12" name="EndDateMonth">
			<span>/</span>
			<input class="reserve-input" type="number" min="1" max="31" name="EndDateDay">
			<span>/</span>
			<input class="reserve-input" type="number" min="2022" max="2023" name="EndDateYear">
			</div>
			
			<h3 class="reserve-label">Start Time (Hr)</h3>
			<div>
			<input class="reserve-input" type="number" min="1" max="24" name="StartTimeHour">
			</div>
			
			<h3 class="reserve-label">End Time (Hr)</h3>
			<div>
			<input class="reserve-input" type="number" min="1" max="24" name="EndTimeHour">
			</div>
			
			<input type="hidden" value='${pid}' name="pid">
			<input type="hidden" value='<%=request.getParameter("name")%>' name="name">
			<input type="hidden" value='<%=request.getParameter("age")%>' name="age">
			<input type="hidden" value='<%=request.getParameter("role")%>' name="role">
			
			<button class="btn reserve-btn" type="submit">Reserve</button>
		</form>
		<a class="btn top-right back-btn" href="javascript:window.history.back();">Go Back &larr;</a>
	</div>
</body>
</html>