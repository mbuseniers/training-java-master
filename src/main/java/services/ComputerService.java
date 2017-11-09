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
		LocalDate dateIntroduced;
		LocalDate dateDiscontinued;

		if (!introduced.equals("")) {
			try {
				dateIntroduced = LocalDate.parse(introduced, formatter);
			} catch (DateTimeParseException e) {
				dateIntroduced = null;
			}
		} else {
			dateIntroduced = null;
		}

		if (!discontinued.equals("")) {
			try {
				dateDiscontinued = LocalDate.parse(discontinued, formatter);
			} catch (DateTimeParseException e) {
				dateDiscontinued = null;

			}
		} else {
			dateDiscontinued = null;
		}

		return da.addComputer(new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id))) == 1;

	}

	// editcomputer JEE
	public boolean editComputer(int id, String name, String introduced, String discontinued, int company_id){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateIntroduced;
		LocalDate dateDiscontinued;

		if (!introduced.equals("")) {
			try {
				dateIntroduced = LocalDate.parse(introduced, formatter);
			} catch (DateTimeParseException e) {
				dateIntroduced = null;
			}
		} else {
			dateIntroduced = null;
		}

		if (!discontinued.equals("")) {
			try {
				dateDiscontinued = LocalDate.parse(discontinued, formatter);
			} catch (DateTimeParseException e) {
				dateDiscontinued = null;

			}
		} else {
			dateDiscontinued = null;
		}

		return da.updateComputer(id,
				new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id))) == 1;

	}

	public int getNumberComputers(){
		return da.getNumberComputers();
	}

	public boolean deleteComputer(String selection){
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





}
