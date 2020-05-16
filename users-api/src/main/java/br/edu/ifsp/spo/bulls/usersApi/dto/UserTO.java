package br.edu.ifsp.spo.bulls.usersApi.dto;


import java.util.Objects;

import lombok.Data;

@Data
public class UserTO {
    private String userName;
    private String email;
    private String password;
    private String token;
    
	@Override
	public boolean equals(Object obj) {
		UserTO other = (UserTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password)
				&& Objects.equals(token, other.token) && Objects.equals(userName, other.userName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, password, token, userName);
	}

	public UserTO(String userName, String email, String password, String token) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.token = token;
	}
    
	public UserTO() {}
    
}
