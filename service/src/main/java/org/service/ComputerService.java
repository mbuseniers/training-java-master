package org.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.core.model.Company;
import org.core.model.Computer;
import org.persistence.ComputerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;



@Service("computerService")
@EnableJpaRepositories(basePackages = "org.persistence")
@Scope("singleton")
public class ComputerService {

	@Autowired
	private ComputerRepository computerRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

	public boolean addComputer(String name, String introduced, String discontinued, int company_id) {
		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		computerRepository.save(new Computer(name, dateIntroduced, dateDiscontinued, new Company(company_id)));
		return true;
	}

	public boolean editComputer(int id, String name, String introduced, String discontinued, int company_id) {
		LocalDate dateIntroduced = this.checkDateIsCorrect(introduced);
		LocalDate dateDiscontinued = this.checkDateIsCorrect(discontinued);
		computerRepository.save((new Computer(id, name, dateIntroduced, dateDiscontinued, new Company(company_id))));
		return true;
	}

	public int getNumberComputers() {
		return (int) computerRepository.count();
	}

	public boolean deleteComputer(String selection) {
		String[] deleteSelected = selection.split(",");
		boolean isDeleteOk = true;

		for (int i = 0; i < deleteSelected.length; i++) {
			computerRepository.deleteById(Integer.valueOf(deleteSelected[i]));
			LOGGER.info("isdeleteok -> " + isDeleteOk);
		}
		return isDeleteOk;
	}

	public ArrayList<Computer> getComputersByLimitAndOffset(int limit, int offset) {
		return computerRepository.findWithLimitOffset(offset, limit);
	}

	public ArrayList<Computer> getComputersByName(String name) {
		return computerRepository.findByName(name);

	}

	public ArrayList<Computer> getComputersByCompanyName(String search) {
		return computerRepository.findByCompanyName(search);
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
