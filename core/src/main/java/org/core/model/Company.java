package org.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="company")
public class Company {
	

	@Id
    @Column(name = "id") 	
	private int id;
	private String name;
	
	
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
	
	public Company(){}
	
	public Company(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Company(int id) {
		super();
		this.id = id;
		this.name = "";
	}
	
	
	public String toString()
	{
		return " ID => " + id + " ----- NAME => " + name;
	}
}
