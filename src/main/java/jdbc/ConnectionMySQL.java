package jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionMySQL {

	
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";

	private static String user = "admincdb";

	private static String passwd = "qwerty1234";

	private static Connection connect;
	

	public static Connection getInstance(){
		if(connect == null){
			try {
			    connect = (Connection) DriverManager.getConnection(url,user,passwd);
			   
			} catch (SQLException ex) {
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}

		}		
		return connect;	
	}	
}
