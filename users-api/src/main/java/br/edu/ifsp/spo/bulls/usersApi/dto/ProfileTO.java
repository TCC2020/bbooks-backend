package br.edu.ifsp.spo.bulls.usersApi.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileTO {

	private int id;

	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotBlank(message = "Lastname is mandatory")
	private String lastName;
	private String country;
	private String city;
	private String state;
	private String birthDate;


	public ProfileTO(int id, String name,
			 String lastName, String country, String city, String state,
			 @NotBlank(message = "Birthdate is mandatory") String birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
	}

	public ProfileTO(String name,
			 String lastName, String country, String city, String state,
			@NotBlank(message = "Birthdate is mandatory") String birthDate) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
	}
	
	public ProfileTO( String country, String city, String state, String birthDate) {
		super();
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
	}

	public ProfileTO() {
	}

}
