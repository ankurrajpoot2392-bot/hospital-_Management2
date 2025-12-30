package hospital_Management_System;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;
public class HospitalManagementSystem {

	private static final String url= "jdbc:mysql://localhost:3306/hospital";
	private static final String username= "root";
	private static final String password= "1ta@Ankur";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
				
				int choice = scanner.nextInt();
				switch(choice) {
				
				case 1:
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					patient.viewPatient();
					System.out.println();
					break;
				case 3:
					doctor.viewDoctor();
					break;
				case 4:
					bookAppointment(patient, doctor, connection, scanner);
					System.out.println();
					break;
				case 5:
					return;
					
					
				}
			}
			 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter the Patient Id: ");
		int patientId = scanner.nextInt();
		System.out.println("Enter the Doctor Id: ");
		int doctorId = scanner.nextInt();
		System.out.println("Enter Appointment Date(YY-MM-YYYY): ");
		String appointmentDate = scanner.nextLine();
		
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvailability(doctorId, appointmentDate, connection)) {
				String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
				try {
				    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
				    preparedStatement.setInt(1, patientId);
				    preparedStatement.setInt(2, doctorId);
				    preparedStatement.setString(3, appointmentDate);
				    int rowAffected = preparedStatement.executeUpdate();
				    if(rowAffected>0) {
				    	System.out.println("Appointment booked! ");
				    }else {
				    	System.out.println("Failed to booked appointment! ");
				    }
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Doctors are not Availabal on this Date: ");
			}
		}else {
			System.out.println("Either doctor or patient does not exist! ");
		}
			
	}

	
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(1, appointmentDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				if(count==0) {
					return true;
				}else {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
 
	}
}


