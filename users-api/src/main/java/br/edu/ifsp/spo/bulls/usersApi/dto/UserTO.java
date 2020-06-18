package br.edu.ifsp.spo.bulls.usersApi.dto;


import java.util.Objects;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserTO {
	@Id
	@NotBlank(message = "UserName is mandatory")
    private String userName;
	
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email format is invalid")
    private String email;
	
	@NotBlank(message = "Password is mandatory")
    private String password;
	
    private String token;
    
    private String uuid;
    
    private Boolean verified ;
    
	@Override
	public boolean equals(Object obj) {
		UserTO other = (UserTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password)
				&& Objects.equals(token, other.token) && Objects.equals(userName, other.userName)
				&& Objects.equals(verified, other.verified) && Objects.equals(uuid, other.uuid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, password, token, userName, verified, uuid);
	}


    
	public UserTO() {}

	public UserTO(@NotBlank(message = "UserName is mandatory") String userName,
			@NotBlank(message = "Email is mandatory") @Email(message = "Email format is invalid") String email,
			@NotBlank(message = "Password is mandatory") String password, String token, String uuid, Boolean verified) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.token = token;
		this.uuid = uuid;
		this.verified = verified;
	}
    
}
