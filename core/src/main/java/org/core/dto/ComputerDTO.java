package org.core.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class ComputerDTO {

	private int id;
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String dateIntroduced;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private String dateDiscontinued;
	private int companyId;
	private String companyName;
	
	public ComputerDTO(){}
	
	public ComputerDTO(int id, String name, String dateIntroduced, String dateDiscontinued, int companyId,
			String companyName) {
		super();
		this.id = id;
		this.name = name;
		this.dateIntroduced = dateIntroduced;
		this.dateDiscontinued = dateDiscontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateIntroduced() {
		return dateIntroduced;
	}
	public void setDateIntroduced(String dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	public String getDateDiscontinued() {
		return dateDiscontinued;
	}
	public void setDateDiscontinued(String dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
