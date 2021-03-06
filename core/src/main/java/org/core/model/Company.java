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
	private Long id;
	private String name;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Company(){}
	
	public Company(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Company(Long id) {
		super();
		this.id = id;
		this.name = "";
	}
	
	
	public String toString()
	{
		return " ID => " + id + " ----- NAME => " + name;
	}
}
