package br.edu.ifsp.spo.bulls.usersApi.dto;


import lombok.Data;

@Data
public class UserTO {
    private String userName;
    private String email;
    private String password;
    private String token;
}
