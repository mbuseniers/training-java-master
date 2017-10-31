package services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import dao.DAOComputer;
import model.Computer;
import utils.Utils;

public class ComputerService {

	private static ComputerService cs;
	DAOComputer da;
	
	private ComputerService()
	{
		da = DAOComputer.getInstance();
	}
	
	public static ComputerService getInstance() {
		if (cs == null)
		{ 	cs = new ComputerService();	
		}
		return cs;
	} 

	
	public int addComputer()
	{
		String nom;
		int id_company;
		
		/* Saisie nom pc*/
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir le nom du Computer ");
		nom = sc.nextLine();
		
		/* Saisie dates */
		System.out.println("Saisie de la date de mise en marche ");
		//String StrDate_inc = sc.nextLine();
		
		System.out.println("Saisir le jour : ");
		int dayOfMonth = sc.nextInt();
		
		System.out.println("Saisir le mois : ");
		int month = sc.nextInt();
		
		System.out.println("Saisir l'année : ");
		int year = sc.nextInt();
		
		LocalDate ts_inc = LocalDate.of(year, month, dayOfMonth);
		
		System.out.println("Saisie de la date de fin de marche ");
		//String StrDate_inc = sc.nextLine();
		
		System.out.println("Saisir le jour : ");
		dayOfMonth = sc.nextInt();
		
		System.out.println("Saisir le mois : ");
		month = sc.nextInt();
		
		System.out.println("Saisir l'année : ");
		year = sc.nextInt();
		
		LocalDate ts_des = LocalDate.of(year, month, dayOfMonth);
		
		/* Saisie company */
		id_company = Utils.chooseCompany();

		
		return da.addComputer(new Computer(nom,ts_inc,ts_des,id_company));
		
	}
	
	public ArrayList<Computer> getComputers() throws SQLException
	{

		return da.getComputers();
	}
	
	public int updateComputer()
	{
		System.out.println("Saisir l'ID du Computer à modifier");
		int id=Utils.scanInt();

		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir le nouveau nom du Computer ");
		String nom = sc.nextLine();
		
		/* Saisie dates */
		
		/* Saisie dates */
		System.out.println("Saisie de la date de mise en marche ");
		//String StrDate_inc = sc.nextLine();
		
		System.out.println("Saisir le jour : ");
		int dayOfMonth = sc.nextInt();
		
		System.out.println("Saisir le mois : ");
		int month = sc.nextInt();
		
		System.out.println("Saisir l'année : ");
		int year = sc.nextInt();
		
		LocalDate ts_inc = LocalDate.of(year, month, dayOfMonth);
		
		System.out.println("Saisie de la date de fin de marche ");
		//String StrDate_inc = sc.nextLine();
		
		System.out.println("Saisir le jour : ");
		dayOfMonth = sc.nextInt();
		
		System.out.println("Saisir le mois : ");
		month = sc.nextInt();
		
		System.out.println("Saisir l'année : ");
		year = sc.nextInt();
		
		LocalDate ts_des = LocalDate.of(year, month, dayOfMonth);
		
		int id_company = Utils.chooseCompany();

		return da.updateComputer(id, new Computer(nom,ts_inc,ts_des,id_company));
		
	}
	
	public int deleteComputer()
	{
		System.out.println("Saisir l'ID du Computer à supprimer");
		int id=Utils.scanInt();
		return da.deleteComputer(id);


	}
	

}
