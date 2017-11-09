package model;

import java.time.LocalDate;

public class Computer {

	private int id;
	private String name;
	private LocalDate dateIntroduced;
	private LocalDate dateDiscontinued;
	private Company company;
	
	
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
	
	public LocalDate getDateIntroduced() {
		return dateIntroduced;
	}
	public void setDateIntroduced(LocalDate date_introduced) {
		this.dateIntroduced = date_introduced;
	}
	public LocalDate getDateDiscontinued() {
		return dateDiscontinued;
	}
	public void setDateDiscontinued(LocalDate date_discontinued) {
		this.dateDiscontinued = date_discontinued;
	}
		
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	public Computer(String name, LocalDate ts_inc, LocalDate ts_des,Company company) {
		super();
		this.name = name;
		this.dateIntroduced = ts_inc;
		this.dateDiscontinued = ts_des;
		this.company = company;
	}
	
	public Computer(int id, String name, LocalDate ts_inc, LocalDate ts_des, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.dateIntroduced = ts_inc;
		this.dateDiscontinued = ts_des;
		this.company = company;
	}
	
	public String toString()
	{
		return "ID : " + id + " -- NOM : " + name + " -- Date Introduced : " + dateIntroduced + " -- Date Discontinued : " + dateDiscontinued + " -- ID COMPANY : " + company.getId();
		
	}

	

}
