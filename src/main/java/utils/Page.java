package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Page {

	
	public static void doCompanyPagination(ResultSet rs)
	{
		Scanner sc = new Scanner(System.in);
		
		try {
			while(!rs.isAfterLast())
			{
				int compteur =0;
				while(compteur < 10 && rs.next())
				{
					int id = rs.getInt(1); 
			    	String nom = rs.getString(2); 
			    	System.out.println("id : "+id+" ----- nom : "+nom);
			    	compteur++;
				}
							
				sc.nextLine();
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//sc.close();
		}
		
	}
	
	
	public static void doComputerPagination(ResultSet rs)
	{
		Scanner sc = new Scanner(System.in);
		
		
		try {
			while(!rs.isAfterLast())
			{
				int compteur =0;
				while(compteur < 10 && rs.next())
				{
					int id = rs.getInt(1); 
			    	String nom = rs.getString(2); 
			    	Date date_inc = rs.getDate(3);
			    	Date date_dis = rs.getDate(4);
			    	int id_company = rs.getInt(5);
			    	
			    	System.out.println("id : "+id+" ----- nom : "+nom+ 
							" ----- date_inc : "+date_inc+" ----- date_des : "+date_dis+ 
							" ----- id_company " +id_company); 			    	compteur++;
				}
							
				sc.nextLine();

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//sc.close();
		}


	}
	


}
