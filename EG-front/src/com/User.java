package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

//Connect to the DB		
					private Connection connect()
					 {
						
					 Connection con = null;
					 
							 try
							 
								 {
									 
									 Class.forName("com.mysql.jdbc.Driver");
									
									 //Provide the correct details: DBServer/DBName, username, password
									 
									 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
								 
								 }
							 
								 catch (Exception e)
										 
										 {
											 
											 e.printStackTrace();
										 
										 
										 }
							 
							 
						 return con;
						 
						 
						 }
		//Read users			
									
				public String readUsers(){
					
					
					
					
				 String output = "";
				 
				 try
						 {
					 
						 Connection con = connect();
						 
						 if (con == null)
							 
						 {
							 return "Error while connecting to the database for reading."; 
							 }
						 
						 // Prepare the html table to be displayed
						 
						 output = "<table border='1'><tr><th>user Name</th><th>Name</th>" +"<th>Phone</th>" +"<th>Email</th>" +"<th>Password</th>" +"<th>Update</th><th>Remove</th></tr>";
						
						 String query = "select * from users";
						 
						 
						 Statement stmt = con.createStatement();
						 
						 
						 ResultSet rs = stmt.executeQuery(query);
						 
						 
						 // iterate through the rows in the result set
						 
						 while (rs.next())
							 
						 {
							 String userID = Integer.toString(rs.getInt("userID"));
							 
							 String userName = rs.getString("userName");
							 
							 String name = rs.getString("name");
							 
							 String phone = rs.getString("phone");
							 
							 String email = rs.getString("email");
							 
							 String password = rs.getString("password");
							 
							
							 
							// Add into the html table
							 output += "<tr><td><input id='hidUserIDUpdate'name='hidUserIDUpdate'type='hidden' value='" + userID + "'>" + userName + "</td>";
							  output += "<td>" + name + "</td>";
							  
							  output += "<td>" + phone + "</td>";
							  
							  output += "<td>" + email + "</td>";
							  
							  output += "<td>" + password + "</td>";
							  
							// buttons
							  
		
							  
							  output += "<td><input name='btnUpdate' type='button' value='Update' "+ "class='btnUpdate btn btn-secondary' data-userid='" + userID + "'></td>" + "<td><input name='btnRemove' type='button' value='Remove' " + "class='btnRemove btn btn-danger' data-userid='" + userID + "'></td></tr>";
						 }
						 
						
						 con.close();
						 
						 // Complete the html table
						
						 output += "</table>";
						 
						 }
						 catch (Exception e)
				 
							 {
							 
							 output = "Error while reading the users.";
							 
							 System.err.println(e.getMessage());
							 
							 
							 }
								 return output;
								 
								 
							}

//insert users to the system
	
	
			public String insertUser(String userName, String name, String phone, String email,String password){
				
				 String output = "";
				 
				 try
				 
				 {
					 
						 Connection con = connect();
						 
						 if (con == null)
							 
						 {
							 return "Error while connecting to the database for inserting!"; 
							 
							 }
						 
						 // create a prepared statement
						 
						 String query = " insert into users(`userID`,`userName`,`name`,`phone`,`email`,`password`)"+ " values (?, ?, ?, ?, ?,?)";
						 
						 PreparedStatement preparedStmt = con.prepareStatement(query);
						 
						 // binding values
						 
						 preparedStmt.setInt(1, 0);
						 
						 preparedStmt.setString(2, userName);
						 
						 preparedStmt.setString(3, name);
						 
						 preparedStmt.setString(4, phone);
						 
						 preparedStmt.setString(5, email);
						 
						 preparedStmt.setString(6, password);
						 
						 // execute the statement
						
						 preparedStmt.execute();
						 
						 con.close();
						 String newUsers = readUsers();
						 output = "{\"status\":\"success\", \"data\": \"" +newUsers + "\"}"; 

						 }
						catch (Exception e)
						{
							 output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";
							 
							 System.err.println(e.getMessage());
							 
							 }
						return output;
						}
			
		//Update  an User	
			
			 public String updateUser(String userID, String userName, String name, String phone, String email,String password){
				 
				 
				 
				   String output = "";
				   
					try
						{
							Connection con = connect();
							
							if (con == null)
								
							{
								return "Error while connecting to the database for updating.";
								
								}
							
							// create a prepared statement
							
							String query = "UPDATE users SET userName=?,name=?,phone=?,email=?,password=?WHERE userID=?";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);
							
							// binding values
							
							preparedStmt.setString(1, userName);
							
							preparedStmt.setString(2, name);
							
							preparedStmt.setString(3, phone);
							
							preparedStmt.setString(4, email);
							
							preparedStmt.setString(5, password);
							
							preparedStmt.setInt(6, Integer.parseInt(userID));
							
							// execute the statement
							
							preparedStmt.execute();
							
							con.close();
							
							 String newUsers = readUsers();
							 
							 output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
							 
							 }
							 catch (Exception e)
							 {
								 output = "{\"status\":\"error\", \"data\":\"Error while updating an User.\"}";
								 
								 System.err.println(e.getMessage());
								 
								 }
					
					 return output;
						 } 
			 
	//Delete an User		 
			 
				public String deleteUser(String userID){
					
					
				String output = "";
				
			  	  try
				
						{
							Connection con = connect();
							
							if (con == null)
								
							{
								return "Error while connecting to the database for deleting.";
								
								}
							
							// create a prepared statement
							
							String query = "delete from users where userID=?";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);
							
							// binding values
							
							preparedStmt.setInt(1, Integer.parseInt(userID));
							
							// execute the statement
							
							preparedStmt.execute();
							
							con.close();
							
							String newUsers = readUsers();
							
							 output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}";
							 
							 }
							catch (Exception e)
							{
								 output = "{\"status\":\"error\", \"data\":\"Error while deleting an User.\"}";
								 
								 System.err.println(e.getMessage());
								 
							 }
			  	  
							return output;
							
							
							}
				
				

}
