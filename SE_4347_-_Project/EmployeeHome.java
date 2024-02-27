package com.SQLInjection.EmployeeHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SQLInjection.Model.AboveAvgFilament;
import com.SQLInjection.Model.Printer;
import com.SQLInjection.Model.Schedule;


@WebServlet("/EmployeeHome")
public class EmployeeHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeeHome() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get user data from query parameters.
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        int role = Integer.parseInt(request.getParameter("role"));
        
        if (role == 0) {
       	
        	// Forward to to the JSP file.
        	response.sendRedirect(request.getContextPath() + "/home.jsp?name=" + name + "&age=" + age + "&role=" + role + "&unauthorized=1");
        } else {

    		// SQL query that will get all 3D printers.
            String query = "SELECT * FROM Printer";
            
            Connection dbConnection = null;
            Statement sqlStatement = null;
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Connect to the database with MySQL user login.
                dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se4347", "root", "root");
                
                // Create SQL prepared statement.
                sqlStatement = dbConnection.createStatement();
                
                // Execute SQL statement against database.
                ResultSet rs = sqlStatement.executeQuery(query);
                
                // Get all printers from the database.
                ArrayList<Printer> printers = new ArrayList<>();
                Printer printer = null;
                
                while (rs.next()) {
                	printer = new Printer();
                	printer.setPid(rs.getInt("Pid"));
                	printer.setSid(rs.getInt("Sid"));
                	printer.setFilamentAmt(rs.getDouble("FilamentAmt"));
                	printer.setFilament(rs.getString("Filament"));
                	
                	printers.add(printer);
                }
                
                // Return home page with all printers displayed.
                request.setAttribute("printers", printers);
                request.setAttribute("name", name);
                request.setAttribute("age", age);
                request.setAttribute("role", role);
                
                // Fetch schedule data from table.
                query = "SELECT * FROM above_avg_price";
                sqlStatement.close();
                // Create SQL prepared statement.
                sqlStatement = dbConnection.createStatement();
                
                ArrayList<AboveAvgFilament> filaments = new ArrayList<>();
                AboveAvgFilament filament = null;
                
                // Execute SQL statement against database.
                rs = sqlStatement.executeQuery(query);
                
                while (rs.next()) {
                	filament = new AboveAvgFilament();
                	filament.setFilamentType(rs.getString("Product"));
                	filament.setFilamentPrice(rs.getDouble("Price"));
                	
                	filaments.add(filament);
                }
                
                request.setAttribute("filaments", filaments);
                sqlStatement.close();
                rs.close();

                // Forward to to the JSP file.
                request.getRequestDispatcher("schedules.jsp").forward(request, response);
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
