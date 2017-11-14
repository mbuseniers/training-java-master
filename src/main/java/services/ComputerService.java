package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import dao.DAOComputer;
import dto.ComputerDTO;
import exceptions.DAOException;
import model.Company;
import model.Computer;

@Service("computerService")
@Scope("singleton")
public class ComputerService {

	@Resource(name="daoComputer")
	private DAOComputer daoComputer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

	public ComputerService(DAOComputer da) {
		this.daoComputer = da;
	}

	public boolean addComputer(String name, String introduced, String discontinued, int company_id) {

		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		try {
			return daoComputer.addComputer(new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id))) == 1;
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean editComputer(int id, String name, String introduced, String discontinued, int company_id) {

		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		try {
			return daoComputer.updateComputer(id,
					new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id))) == 1;
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public int getNumberComputers() {
		try {
			return daoComputer.getNumberComputers();
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
				isDeleteOk = daoComputer.deleteComputer(Integer.valueOf(deleteSelected[i])) && isDeleteOk;
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
			listComputers = daoComputer.getComputersByLimitAndOffset(limit, offset);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listComputers;
	}

	public ArrayList<ComputerDTO> getComputersByName(String name) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();

		try {
			listComputers = daoComputer.getComputersByName(name);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listComputers;
	}

	public ArrayList<ComputerDTO> getComputersByCompanyName(String search) {
		ArrayList<ComputerDTO> listComputers = new ArrayList<>();
		try {
			listComputers = daoComputer.getComputersByCompanyName(search);
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
