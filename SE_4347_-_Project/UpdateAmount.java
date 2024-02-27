package com.SQLInjection.UpdateAmount;

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
import com.SQLInjection.Model.Supplier;


@WebServlet("/UpdateAmount/*")
public class UpdateAmount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateAmount() {
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
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se4347", "root", "root");
            
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
            query = "SELECT * FROM Supplier WHERE Product =?";
            
            // Create SQL prepared statement.
            sqlStatement = dbConnection.prepareStatement(query);
            sqlStatement.setString(1, printer.getFilament());
            
            ArrayList<Supplier> suppliers = new ArrayList<>();
            Supplier supplier = null;
            
            // Execute SQL statement against database.
            rs = sqlStatement.executeQuery();
            
            while (rs.next()) {
            	supplier = new Supplier();
            	supplier.setSupId(rs.getInt("Sid")); 
            	supplier.setProducte(rs.getString("Product"));
            	supplier.setPrice(rs.getDouble("Price"));
            	
            	suppliers.add(supplier);
            }
            
            request.setAttribute("suppliers", suppliers);
            request.setAttribute("printer", printer);
            
            rs.close();
            
            // Forward to to the JSP file.
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/updateAmount.jsp");
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
		double amount = Integer.parseInt(request.getParameter("amount"));
		amount = amount / 100;
		String pid = request.getParameter("pid");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
        String role = request.getParameter("role");
        
        Connection dbConnection = null;
        PreparedStatement sqlStatement = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database with MySQL user login.
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se4347", "root", "root");
            	
            	String query = "UPDATE Printer SET FilamentAmt=? WHERE PID =?";
            	
                PreparedStatement updateFilamntAmt = dbConnection.prepareStatement(query);
                
                updateFilamntAmt.setDouble(1, amount);
                updateFilamntAmt.setInt(2, Integer.parseInt(pid));
                
                updateFilamntAmt.executeUpdate();

                updateFilamntAmt.close();
	                              
	        	// Forward to to the JSP file.
	        	response.sendRedirect(request.getContextPath() + "/home.jsp?name=" + name + "&age=" + age + "&role=" + role + "&update=1");
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
