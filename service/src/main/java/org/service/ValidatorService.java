package org.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class ValidatorService {
	
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
