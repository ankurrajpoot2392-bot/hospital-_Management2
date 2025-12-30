package hospital_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.util.Scanner;

public class Doctor {

private Connection connection;
	
	
	
	public Doctor(Connection connection) {
		this.connection= connection;
		
		
		}
	   
	    
	    public void viewDoctor() {
	    	String query = "select * from doctors";
	    	try {
	    		PreparedStatement preparedStatement = connection.prepareStatement(query);
	    		ResultSet resultSet = preparedStatement.executeQuery();
	    		System.out.print("doctors :");
	    		while(resultSet.next()) {
	    			int id = resultSet.getInt("id");
	    			String name = resultSet.getString("name");
	    			String specilization = resultSet.getString("specilization");
	    			System.out.println(id+" "+name+" "+specilization);
	    		}
	    		
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    	
	    	public boolean getDoctorById(int id) {
	    		String query = "SELECT * FROM doctors WHERE id = ?";
	    		try {
	    			PreparedStatement preparedStatement = connection.prepareStatement(query);
	    			preparedStatement.setInt(1, id);
	    			ResultSet resultSet = preparedStatement.executeQuery();
	    			if(resultSet.next()) {
	    				return true;
	    			}else {
	    				return false;
	    			}
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
	    		return false; 
	    	
	    }
}
