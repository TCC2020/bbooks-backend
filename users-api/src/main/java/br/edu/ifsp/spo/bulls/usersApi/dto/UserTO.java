package br.edu.ifsp.spo.bulls.usersApi.dto;

import lombok.Data;

@Data
public class UserTO {
    private String email;
    private String userName;
    private String password;
    private String token;
}
