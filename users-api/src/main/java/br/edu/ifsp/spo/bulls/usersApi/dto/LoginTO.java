package br.edu.ifsp.spo.bulls.usersApi.dto;

import lombok.Data;

@Data
public class LoginTO {
    private String userName;
    private String email;
    private String password;

    public LoginTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
