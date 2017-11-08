package services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOComputer;
import model.Company;
import model.Computer;
import utils.Utils;

public class ComputerService {

	private static ComputerService cs;
	private DAOComputer da;
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOComputer.class);

	
	private ComputerService() {
		da = DAOComputer.getInstance();
	}

	public static ComputerService getInstance() {
		if (cs == null) {
			cs = new ComputerService();
		}
		return cs;
	}

	// addComputer JEE
	public boolean addComputer(String name, String introduced, String discontinued, int company_id){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date_introduced;
		LocalDate date_discontinued;

		if (!introduced.equals("")) {
			try {
				date_introduced = LocalDate.parse(introduced, formatter);
			} catch (DateTimeParseException e) {
				date_introduced = null;
			}
		} else {
			date_introduced = null;
		}

		if (!discontinued.equals("")) {
			try {
				date_discontinued = LocalDate.parse(discontinued, formatter);
			} catch (DateTimeParseException e) {
				date_discontinued = null;

			}
		} else {
			date_discontinued = null;
		}

		return da.addComputer(new Computer(name, date_introduced, date_discontinued, new Company(company_id))) == 1;

	}

	// editcomputer JEE
	public boolean editComputer(int id, String name, String introduced, String discontinued, int company_id){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date_introduced;
		LocalDate date_discontinued;

		if (!introduced.equals("")) {
			try {
				date_introduced = LocalDate.parse(introduced, formatter);
			} catch (DateTimeParseException e) {
				date_introduced = null;
			}
		} else {
			date_introduced = null;
		}

		if (!discontinued.equals("")) {
			try {
				date_discontinued = LocalDate.parse(discontinued, formatter);
			} catch (DateTimeParseException e) {
				date_discontinued = null;

			}
		} else {
			date_discontinued = null;
		}

		return da.updateComputer(id,
				new Computer(name, date_introduced, date_discontinued, new Company(company_id))) == 1;

	}

	public int getNumberComputers(){
		return da.getNumberComputers();
	}

	public boolean deleteComputer(String selection) throws NumberFormatException, SQLException {
		String[] deleteSelected = selection.split(",");
		boolean isDeleteOk = true;

		for (int i = 0; i < deleteSelected.length; i++) {
			isDeleteOk = da.deleteComputer(Integer.valueOf(deleteSelected[i])) && isDeleteOk;
		    LOGGER.info("isdeleteok -> " + isDeleteOk);
		}
		return isDeleteOk;
	}
	

	public ArrayList<Computer> getComputersByLimitAndOffset(int limit, int offset){
		return da.getComputersByLimitAndOffset(limit, offset);
	}
	
	public ArrayList<Computer> getComputersByName(String name){
		return da.getComputersByName(name);
	}
	
	public ArrayList<Computer> getComputersByCompanyName(String search) {
		return da.getComputersByCompanyName(search);
	}

	public ArrayList<Computer> getComputers(){

		return da.getComputers();
	}

	public boolean deleteComputer(){
		System.out.println("Saisir l'ID du Computer à supprimer");
		int id = Utils.scanInt();
		return da.deleteComputer(id);

	}

	// addComputer interface graphique
	public int addComputer(){
		String nom;
		int id_company;

		/* Saisie nom pc */
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir le nom du Computer ");
		nom = sc.nextLine();

		/* Saisie dates */
		System.out.println("Saisie de la date de mise en marche ");
		// String StrDate_inc = sc.nextLine();

		System.out.println("Saisir le jour : ");
		int dayOfMonth = sc.nextInt();

		System.out.println("Saisir le mois : ");
		int month = sc.nextInt();

		System.out.println("Saisir l'année : ");
		int year = sc.nextInt();

		LocalDate ts_inc = LocalDate.of(year, month, dayOfMonth);

		System.out.println("Saisie de la date de fin de marche ");
		// String StrDate_inc = sc.nextLine();

		System.out.println("Saisir le jour : ");
		dayOfMonth = sc.nextInt();

		System.out.println("Saisir le mois : ");
		month = sc.nextInt();

		System.out.println("Saisir l'année : ");
		year = sc.nextInt();

		LocalDate ts_des = LocalDate.of(year, month, dayOfMonth);

		/* Saisie company */
		id_company = Utils.chooseCompany();

		return da.addComputer(new Computer(nom, ts_inc, ts_des, new Company(id_company)));

	}

	// updateComputer pour l'interface graphique
	public int updateComputer() throws SQLException {
		System.out.println("Saisir l'ID du Computer à modifier");
		int id = Utils.scanInt();

		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir le nouveau nom du Computer ");
		String nom = sc.nextLine();

		/* Saisie dates */

		/* Saisie dates */
		System.out.println("Saisie de la date de mise en marche ");
		// String StrDate_inc = sc.nextLine();

		System.out.println("Saisir le jour : ");
		int dayOfMonth = sc.nextInt();

		System.out.println("Saisir le mois : ");
		int month = sc.nextInt();

		System.out.println("Saisir l'année : ");
		int year = sc.nextInt();

		LocalDate ts_inc = LocalDate.of(year, month, dayOfMonth);

		System.out.println("Saisie de la date de fin de marche ");
		// String StrDate_inc = sc.nextLine();

		System.out.println("Saisir le jour : ");
		dayOfMonth = sc.nextInt();

		System.out.println("Saisir le mois : ");
		month = sc.nextInt();

		System.out.println("Saisir l'année : ");
		year = sc.nextInt();

		LocalDate ts_des = LocalDate.of(year, month, dayOfMonth);

		int id_company = Utils.chooseCompany();

		return da.updateComputer(id, new Computer(nom, ts_inc, ts_des, new Company(id_company)));

	}

	


}
