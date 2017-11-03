package services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.DAOComputer;
import model.Company;
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

		
		return da.addComputer(new Computer(nom,ts_inc,ts_des,new Company(id_company)));
		
	}
	
	public String addComputer(String name, String introduced, String discontinued, int company_id)
	{
		boolean isNameOk=false, isIntroducedOk=false, isDiscontinuedOk=false, isCompanyOk=false;
		String message_erreurs ="";
		
		if(name != "") {
			isNameOk=true;
		}else {
			message_erreurs += "Nom vide, \n";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date_introduced;
		LocalDate date_discontinued;
		
		try {
			date_introduced = LocalDate.parse(introduced, formatter);
			isIntroducedOk=true;
		}catch(DateTimeParseException e) {
			message_erreurs += "Mauvaise syntax pour introduced, \n";
			date_introduced = null;

		}
		
		try {
			date_discontinued = LocalDate.parse(discontinued, formatter);
			isDiscontinuedOk=true;
		}catch(DateTimeParseException e) {
			message_erreurs += "Mauvaise syntax pour discontinued, \n";
			date_discontinued = null;

		}

		
		if(company_id > 0 && company_id <= 45) {
			isCompanyOk=true;
		}else {
			message_erreurs += "Company invalide";
		}
		
		if(isNameOk && isIntroducedOk && isDiscontinuedOk && isCompanyOk) {
			if(da.addComputer(new Computer(name,date_introduced,date_discontinued,new Company(company_id))) == 1) {
				return "Ajout Ok";
			}else {
				return "Problème lors de l'ajout";
			}

		}else {
			return message_erreurs;
		}
		
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

		return da.updateComputer(id, new Computer(nom,ts_inc,ts_des,new Company(id_company)));
		
	}
	
	public int deleteComputer()
	{
		System.out.println("Saisir l'ID du Computer à supprimer");
		int id=Utils.scanInt();
		return da.deleteComputer(id);


	}
	

}
