package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOComputer;
import dto.ComputerDTO;
import exceptions.DAOException;
import model.Company;
import model.Computer;

public class ComputerService {

	private static ComputerService cs;
	private DAOComputer da;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService() {
		da = DAOComputer.getInstance();
	}

	public static ComputerService getInstance() {
		if (cs == null) {
			cs = new ComputerService();
		}
		return cs;
	}

	public boolean addComputer(String name, String introduced, String discontinued, int company_id) {

		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		try {
			return da.addComputer(new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id))) == 1;
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean editComputer(int id, String name, String introduced, String discontinued, int company_id) {

		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		try {
			return da.updateComputer(id,
					new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id))) == 1;
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public int getNumberComputers() {
		try {
			return da.getNumberComputers();
		} catch (DAOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean deleteComputer(String selection) {
		String[] deleteSelected = selection.split(",");
		boolean isDeleteOk = true;

		for (int i = 0; i < deleteSelected.length; i++) {
			try {
				isDeleteOk = da.deleteComputer(Integer.valueOf(deleteSelected[i])) && isDeleteOk;
			} catch (DAOException e) {
				e.printStackTrace();
				return false;
			}
			LOGGER.info("isdeleteok -> " + isDeleteOk);
		}
		return isDeleteOk;
	}

	public ArrayList<ComputerDTO> getComputersByLimitAndOffset(int limit, int offset) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();

		try {
			listComputers = da.getComputersByLimitAndOffset(limit, offset);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listComputers;
	}

	public ArrayList<ComputerDTO> getComputersByName(String name) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();

		try {
			listComputers = da.getComputersByName(name);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listComputers;
	}

	public ArrayList<ComputerDTO> getComputersByCompanyName(String search) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();
		try {
			listComputers = da.getComputersByCompanyName(search);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listComputers;
	}

	public LocalDate checkDateIsCorrect(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate;

		if (!date.isEmpty()) {
			try {
				localDate = LocalDate.parse(date, formatter);
				return localDate;
			} catch (DateTimeParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}

}
