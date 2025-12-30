package hospital_Management_System;
import java.sql.*;
import java.util.*;
public class Patient {

	private Connection connection;
	
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner) {
		this.connection= connection;
		this.scanner= scanner;
		
		}
	    public void addPatient() {
		System.out.print("Enter the patient Name :");
		String name= scanner.next();	
		System.out.print("Enter the patient Age :");
		int age = scanner.nextInt();
		System.out.print("Enter the patient Gender :");
		String gender = scanner.next();
		try {
			String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows=preparedStatement.executeUpdate();
			if(affectedRows>0) {
				System.out.print("Patient aided Succesfully!!");
			}else {
				System.out.print("Failed to add paitent!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		
   }
	    
	    public void viewPatient() {
	    	String query = "select * from patients";
	    	try {
	    		PreparedStatement preparedStatement = connection.prepareStatement(query);
	    		ResultSet resultSet = preparedStatement.executeQuery();
	    		System.out.print("Patient :");
	    		while(resultSet.next()) {
	    			int id = resultSet.getInt("id");
	    			String name = resultSet.getString("name");
	    			int age = resultSet.getInt("age");
	    			String gender = resultSet.getString("gender");
	    			System.out.print(id+" "+name+" "+age+" "+gender);
	    			
	    		}
	    		
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    	
	    	public boolean getPatientById(int id) {
	    		String query = "SELECT * FROM patients WHERE id = ?";
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
