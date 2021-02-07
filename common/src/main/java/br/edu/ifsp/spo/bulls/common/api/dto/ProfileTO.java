package br.edu.ifsp.spo.bulls.common.api.dto;

import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Objeto de trânsito: Perfil usuário ")
public class ProfileTO {

	@ApiModelProperty(value = "Idedntificador")
	private int id;

	@ApiModelProperty(value = "Nome")
	@NotBlank(message = "Name is mandatory")
	private String name;

	@ApiModelProperty(value = "Sobrenome")
	@NotBlank(message = "Lastname is mandatory")
	private String lastName;

	@ApiModelProperty(value = "País")
	private String country;

	@ApiModelProperty(value = "Cidade")
	private String city;

	@ApiModelProperty(value = "Estado")
	private String state;

	@ApiModelProperty(value = "Data Nascimento")
	private String birthDate;

	@ApiModelProperty(value = "URL da imagem de perfil")
	private String profileImage;

	private String friendshipStatus;

	private String username;

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
