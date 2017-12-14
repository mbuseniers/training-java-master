package org.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.core.dto.ComputerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ComputerValidator implements Validator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("validate from custom validator");
		ComputerDTO computerDto = (ComputerDTO) target;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		boolean isIntroducedOk = false, isDiscontinuedOk = false;

		if (computerDto.getName().equals("")) {
			errors.rejectValue("name", "validator.name");
		}

		computerDto.getDateIntroduced();

		if (!computerDto.getDateIntroduced().equals("")) {
			try {
				LocalDate.parse(computerDto.getDateIntroduced(), formatter);
				isIntroducedOk = true;
			} catch (DateTimeParseException e) {
				errors.rejectValue("dateIntroduced", "validator.introduced");
			}
		}

		if (!computerDto.getDateDiscontinued().equals("")) {
			try {
				LocalDate.parse(computerDto.getDateDiscontinued(), formatter);
				isDiscontinuedOk = true;
			} catch (DateTimeParseException e) {
				errors.rejectValue("dateDiscontinued", "validator.discontinued");
			}
		}

		if (isDiscontinuedOk && isIntroducedOk) {
			LocalDate introduced = LocalDate.parse(computerDto.getDateIntroduced());
			LocalDate discontinued = LocalDate.parse(computerDto.getDateDiscontinued());
			if (discontinued.isBefore(introduced)) {
				errors.rejectValue("dateDiscontinued", "validator.discontinuedBeforeIntroduced");
			}
		}
	}
}
