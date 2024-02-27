package com.SQLInjection.Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmployeeLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

 // Get request to /login
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Return index.jsp as response.
    	RequestDispatcher rd = request.getRequestDispatcher("employeeLogin.jsp");
    	rd.forward(request, response);
    }

    // Post request to /login.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hello");
		// Get user data from query parameters.
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        
        // SQL query that will inject unsafe input from user.
        String query = "SELECT * FROM Employee WHERE Name=? AND Age=?";
        
        Connection dbConnection = null;
        PreparedStatement sqlStatement = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database with MySQL user login.
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se4347", "root", "root");
            
            // Create SQL prepared statement.
            sqlStatement = dbConnection.prepareStatement(query);
            sqlStatement.setString(1, name);
            sqlStatement.setInt(2, age);
            
            // Execute SQL statement against database.
            ResultSet rs = sqlStatement.executeQuery();
            
            if (rs.next()) {
            	
            	// If customer already in database, return home page.
            	response.sendRedirect(request.getContextPath() + "/home.jsp?name=" + name + "&age=" + age + "&role=1");
            	
            } else {
            	
            	// Get number of rows in customer table.
            	Statement rowCountStatement = null;
            	
            	query = "SELECT COUNT(*) FROM Employeer";
            	
            	rowCountStatement = dbConnection.createStatement();
            	
                ResultSet rowCountResult = rowCountStatement.executeQuery(query);
                
                rowCountResult.next();
                
                int rowCount = rowCountResult.getInt(1);
                
            	// If customer not already in database, insert into customer table .
                PreparedStatement CreateCustomerStatement = null;

            	query = "INSERT INTO Employee (EID, Name, Age) VALUES (?, ?, ?)";
            	
            	CreateCustomerStatement = dbConnection.prepareStatement(query);
            	
            	CreateCustomerStatement.setInt(1, rowCount+1);
            	CreateCustomerStatement.setString(2, name);
            	CreateCustomerStatement.setInt(3, age);
            	
                CreateCustomerStatement.execute();
                
                // Return home page.
                response.sendRedirect(request.getContextPath() + "/home.jsp?name=" + name + "&age=" + age + "&role=1");
                
                // Close SQL statement.
                rowCountStatement.close();
                CreateCustomerStatement.close();
            }
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
