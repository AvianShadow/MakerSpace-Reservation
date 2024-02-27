package com.SQLInjection.ViewSchedule;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SQLInjection.Model.PrinterReserve;
import com.SQLInjection.Model.Schedule;

@WebServlet("/ViewSchedule/*")
public class ViewSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ViewSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get user data from query parameters.
		String pid = request.getPathInfo().substring(1);
		String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        int role = Integer.parseInt(request.getParameter("role"));
        
        // Fetch printer data from table.
        String query = "SELECT * FROM Printer WHERE Pid=?";
        
        Connection dbConnection = null;
        PreparedStatement sqlStatement = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database with MySQL user login.
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se4347", "root", "Smiley@8730!");
            
            // Create SQL prepared statement.
            sqlStatement = dbConnection.prepareStatement(query);
            sqlStatement.setString(1, pid);
            
            // Execute SQL statement against database.
            ResultSet rs = sqlStatement.executeQuery();
            
            // Get all printers from the database.
            PrinterReserve printer = null;
            
            while (rs.next()) {
            	printer = new PrinterReserve();
            	printer.setSid(rs.getInt("Sid"));
            	printer.setFilamentAmt(rs.getDouble("FilamentAmt"));
            	printer.setFilament(rs.getString("Filament"));            	
            }
            
            // Return home page with all printers displayed.
            request.setAttribute("name", name);
            request.setAttribute("age", age);
            request.setAttribute("role", role);
            request.setAttribute("pid", pid);
            
            rs.close();
        	sqlStatement.close();
            
            // Fetch schedule data from table.
            query = "SELECT c.Name, s.StartDate, s.EndDate, s.StartTime, s.EndTime FROM schedules s, list_usage l, customer c WHERE s.SID2=l.SID2 AND s.CID=c.CID AND l.PID =?";
            
            // Create SQL prepared statement.
            sqlStatement = dbConnection.prepareStatement(query);
            sqlStatement.setString(1, pid);
            
            ArrayList<Schedule> schedules = new ArrayList<>();
            Schedule schedule = null;
            
            // Execute SQL statement against database.
            rs = sqlStatement.executeQuery();
            
            while (rs.next()) {
            	schedule = new Schedule();
            	schedule.setName(rs.getString("Name"));
            	schedule.setStartDate(rs.getString("StartDate"));
            	schedule.setEndDate(rs.getString("EndDate"));     
            	schedule.setStartTime(rs.getString("StartTime"));            	
            	schedule.setEndTime(rs.getString("EndTime"));  
            	
            	schedules.add(schedule);
            }
            
            request.setAttribute("schedules", schedules);
            request.setAttribute("printer", printer);
            
            rs.close();
            
            // Forward to to the JSP file.
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/viewSchedule.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	
            	// Close SQL statement and connection.
            	sqlStatement.close();
            	dbConnection.close();
            } catch (Exception e) {}
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String startDateMonth = request.getParameter("StartDateMonth");
		String startDateDay = request.getParameter("StartDateDay");
		String startDateYear = request.getParameter("StartDateYear");
		String endDateMonth = request.getParameter("EndDateMonth");
		String endDateDay = request.getParameter("EndDateDay");
		String endDateYear = request.getParameter("EndDateYear");
		String startTimeHour = request.getParameter("StartTimeHour");
		String endTimeHour = request.getParameter("EndTimeHour");
		String pid = request.getParameter("pid");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
        String role = request.getParameter("role");

		
		String startDate = startDateMonth + "/" + startDateDay + "/" + startDateYear;
		String endDate = endDateMonth + "/" + endDateDay + "/" + endDateYear;
		startTimeHour += ":00";
		endTimeHour += ":00";
        
        Connection dbConnection = null;
        PreparedStatement sqlStatement = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database with MySQL user login.
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se4347", "root", "root");
            
            	// Get number of rows in schedule table.
            	Statement scheduleRowCountStatement = null;
            	
            	String query = "SELECT COUNT(*) FROM Schedules";
            	
            	scheduleRowCountStatement = dbConnection.createStatement();
            	
                ResultSet rowCountResult = scheduleRowCountStatement.executeQuery(query);
                
                rowCountResult.next();
                
                int scheduleRowCount = rowCountResult.getInt(1);
                
                scheduleRowCountStatement.close();
                
                query = "SELECT Cid FROM Customer WHERE Name=? AND Age=?";
                
                PreparedStatement SelectCid = null;
            	
                SelectCid = dbConnection.prepareStatement(query);
            	
                SelectCid.setString(1, name);
                SelectCid.setString(2, age);
            	
                ResultSet rs = SelectCid.executeQuery();
                
                int cid = 0;
                
                while (rs.next()) {
                	cid = rs.getInt("Cid");         	
                }
                                
                rs.close();
                
                // Insert schedule into table.
                PreparedStatement CreateScheduleStatement = null;

            	query = "INSERT INTO Schedules (SID2, CID, StartDate, EndDate, StartTime, EndTime) VALUES (?, ?, ?, ?, ?, ?)";
            	
            	CreateScheduleStatement = dbConnection.prepareStatement(query);
            	
            	CreateScheduleStatement.setInt(1, scheduleRowCount+1);
            	CreateScheduleStatement.setInt(2, cid);
            	CreateScheduleStatement.setString(3, startDate);
            	CreateScheduleStatement.setString(4, endDate);
            	CreateScheduleStatement.setString(5, startTimeHour);
            	CreateScheduleStatement.setString(6, endTimeHour);

            	CreateScheduleStatement.execute();
            	
            	CreateScheduleStatement.close();
            	
            	// Insert reserve into table.
                PreparedStatement CreateReserveStatement = null;

            	query = "INSERT INTO reserves (SID2, CID, PID) VALUES (?, ?, ?)";
            	
            	CreateReserveStatement = dbConnection.prepareStatement(query);
            	
            	CreateReserveStatement.setInt(1, scheduleRowCount+1);
            	CreateReserveStatement.setInt(2, cid);
            	CreateReserveStatement.setInt(3, Integer.parseInt(pid));

            	CreateReserveStatement.execute();
            	
            	CreateReserveStatement.close();
            	
	        	request.setAttribute("printer", pid);
	                              
	        	// Forward to to the JSP file.
	        	response.sendRedirect(request.getContextPath() + "/home.jsp?name=" + name + "&age=" + age + "&role=" + role + "&reserve=1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	
            	// Close SQL statement and connection.
            	dbConnection.close();
            } catch (Exception e) {}
        }
		
	}

}
