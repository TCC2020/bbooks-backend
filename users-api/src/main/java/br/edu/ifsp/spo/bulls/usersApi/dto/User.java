package br.edu.ifsp.spo.bulls.usersApi.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "User")
public class User {
	
	@Id 
	@GeneratedValue
	private Long id;
	private String userName;
	private String email;
	private String password;
	
	
	public User(Long id, String userName, String email, String senha) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = senha;
	}
	
	public User() {
		
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
