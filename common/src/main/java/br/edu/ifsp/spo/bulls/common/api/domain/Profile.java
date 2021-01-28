package br.edu.ifsp.spo.bulls.common.api.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: Usuarios ")
@Table(name = "profiles", indexes = {
		@Index(name = "IDX_PROFILE_NAME", columnList = "name"), 
		@Index(name = "IDX_PROFILE_LASTNAME", columnList = "lastName") })
public class Profile {

	@Id
	@ApiModelProperty(value = "Identificador")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "Name is mandatory")
	@ApiModelProperty(value = "Nome do usuário")
	private String name;
	
	@NotBlank(message = "lastName is mandatory")
	@ApiModelProperty(value = "Sobrenome do usuário")
	private String lastName;

	@ApiModelProperty(value = "País")
	private String country;
	@ApiModelProperty(value = "Cidade")
	private String city;
	@ApiModelProperty(value = "Estado")
	private String state;

	@ApiModelProperty(value = "Data Nascimento")
	private String birthDate;
	
	@OneToOne
	@ApiModelProperty(value = "Cadastro de login do perfil")
	private User user;

	@ApiModelProperty(value = "URL da imagem de perfil")
	private String profileImage;

	public Profile() {
		
	}


	public Profile(int id, @NotBlank(message = "Name is mandatory") String name,
			@NotBlank(message = "lastName is mandatory") String lastName, String country, String city, String state,
			@NotBlank(message = "Birthdate is mandatory") String birthDate, User user) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
		this.user = user;
	}


	public Profile(@NotBlank(message = "Name is mandatory") String name,
			@NotBlank(message = "lastName is mandatory") String lastName, String country, String city, String state,
			@NotBlank(message = "Birthdate is mandatory") String birthDate, User user) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.state = state;
		this.birthDate = birthDate;
		this.user = user;
	}


	public Profile(@NotBlank(message = "Name is mandatory") String name,
			@NotBlank(message = "lastName is mandatory") String lastName,
			String profileImage, User user) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.user = user;
		this.profileImage = profileImage;
	}
	
}
