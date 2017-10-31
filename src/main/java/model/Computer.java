package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;

public class Computer {

	private int id;
	private String name;
	private LocalDate date_introduced;
	private LocalDate date_discontinued;
	

	private int id_company;
	
	
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
	
	public LocalDate getDate_introduced() {
		return date_introduced;
	}
	public void setDate_introduced(LocalDate date_introduced) {
		this.date_introduced = date_introduced;
	}
	public LocalDate getDate_discontinued() {
		return date_discontinued;
	}
	public void setDate_discontinued(LocalDate date_discontinued) {
		this.date_discontinued = date_discontinued;
	}
		
	
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	
	public Computer(String name, LocalDate ts_inc, LocalDate ts_des, int id_company) {
		super();
		this.name = name;
		this.date_introduced = ts_inc;
		this.date_discontinued = ts_des;
		this.id_company = id_company;
	}
	
	public Computer(int id, String name, LocalDate ts_inc, LocalDate ts_des, int id_company) {
		super();
		this.id = id;
		this.name = name;
		this.date_introduced = ts_inc;
		this.date_discontinued = ts_des;
		this.id_company = id_company;
	}
	
	public String toString()
	{
		return "ID : " + id + " -- NOM : " + name + " -- Date Introduced : " + date_introduced + " -- Date Discontinued : " + date_discontinued + " -- ID COMPANY : " + id_company;
		
	}
	

}
