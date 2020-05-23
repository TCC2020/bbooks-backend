package br.edu.ifsp.spo.bulls.usersApi.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

	@Id
	private String id;
	
	@NotBlank(message = "Fullname is mandatory")
	private String fullName;
	
	private String country;
	private String city;
	private String state;
	
	@NotBlank(message = "Birthdate is mandatory")
	private String birthDate;
	
	@OneToOne
	private User user;
	
}
