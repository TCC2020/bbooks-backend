package br.edu.ifsp.spo.bulls.usersApi.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import lombok.Data;

@Data
public class ProfileTO {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "Fullname is mandatory")
	private String fullName;
	
	private String country;
	private String city;
	private String state;
	
	@NotBlank(message = "Birthdate is mandatory")
	private String birthDate;
	
	@OneToOne
	private User user;

	public ProfileTO(int id, @NotBlank(message = "Fullname is mandatory") String fullName, String country, String city,
			String state, @NotBlank(message = "Birthdate is mandatory") String birthDate, User user) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
		this.user = user;
	}

	public ProfileTO() {
	}

	public ProfileTO(@NotBlank(message = "Fullname is mandatory") String fullName, String country, String city,
			String state, @NotBlank(message = "Birthdate is mandatory") String birthDate, User user) {
		super();
		this.fullName = fullName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
		this.user = user;
	}
	
	
}
