package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Objeto de trânsito: login ")
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
