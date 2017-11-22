package model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "computer")
public class Computer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
    @Column(name = "introduced")
	private LocalDate dateIntroduced;
    @Column(name = "discontinued")
	private LocalDate dateDiscontinued;
    @ManyToOne(targetEntity = Company.class)   
    private Company company;
	
	
	public int getId() {
	      System.out.println("Your ID : " + id);
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
	
	public Computer() {};
	
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
