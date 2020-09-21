package br.edu.ifsp.spo.bulls.usersApi.dto;

import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Objeto de trânsito: Usuario ")
public class UserTO {
	
	private UUID id;
	@NotBlank(message = "UserName is mandatory")
    private String userName;
	
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email format is invalid")
    private String email;
	
    private String token;

    private String idToken;

    private String idSocial;
    
    private Boolean verified;

    private ProfileTO profile;

    
	@Override
	public boolean equals(Object obj) {
		UserTO other = (UserTO) obj;
		return Objects.equals(email, other.email)
				&& Objects.equals(token, other.token) && Objects.equals(userName, other.userName)
				&& Objects.equals(verified, other.verified);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, token, userName, verified);
	}

	public UserTO() {}

	public UserTO(String userName,String email, String token, Boolean verified) {
		super();
		this.userName = userName;
		this.email = email;
		this.token = token;
		this.verified = verified;
	}
	
	public UserTO(String userName,String email, Boolean verified) {
		super();
		this.userName = userName;
		this.email = email;
		this.verified = verified;
	}

	public UserTO(String userName,String email) {
		super();
		this.userName = userName;
		this.email = email;
	}
    
}
