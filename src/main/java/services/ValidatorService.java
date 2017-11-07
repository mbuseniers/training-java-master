package services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ValidatorService {

	private static ValidatorService validatorService;
	
	private ValidatorService() {
	}

	public static ValidatorService getInstance() {
		if (validatorService == null) {
			validatorService = new ValidatorService();
		}
		return validatorService;
	}
	
	public boolean checkName(String name) {
		return !name.equals("");
	}
	
	public boolean checkCompany(int companyId, int nombreCompanies) {
		return companyId > 0 && companyId <= nombreCompanies;
	}
	
	public boolean checkDateFromString(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if(!dateString.equals("")) {
			try {
				LocalDate.parse(dateString, formatter);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		}else {
			return true;
		}
	}
	
}
