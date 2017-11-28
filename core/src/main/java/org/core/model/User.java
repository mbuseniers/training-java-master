package org.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	private int iduser;

	@Column
	private String name;

	@Column
	private String password;

	public String getLogin() {
		return name;
	}

	public void setLogin(String login) {
		this.name = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return iduser;
	}

	public void setId(int id) {
		this.iduser = id;
	}

}