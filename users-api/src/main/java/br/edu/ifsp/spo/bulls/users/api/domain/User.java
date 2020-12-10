package br.edu.ifsp.spo.bulls.users.api.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: Login ")
@Table(name = "users")
public class User implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3104545566617263249L;
	
	@Id
	@ApiModelProperty(value = "Identificador")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

	@ApiModelProperty(value = "Nome de usuário")
	@NotBlank(message = "UserName is mandatory")
    private String userName;

	@ApiModelProperty(value = "Email")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is invalid")
    private String email;

	@ApiModelProperty(value = "Senha")
    @NotBlank(message = "Password is mandatory")
    private String password;

	@ApiModelProperty(value = "Token de login")
    private String token;

	@ApiModelProperty(value = "Token de login com o google")
    private String idSocial;

	@ApiModelProperty(value = "Data de criação")
    private LocalDateTime creationDate;

	@ApiModelProperty(value = "Indica se o usuário tem o cadastro confirmado")
    private Boolean verified ;
    
    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
        verified = false;
    }

	public User(String userName, @NotBlank(message = "Email is mandatory") String email,
			@NotBlank(message = "Password is mandatory") String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public User() {
		
	}
}
