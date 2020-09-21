package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Objeto de trânsito: login ")
public class LoginTO {
    @ApiModelProperty(value = "Nome de usuário")
    private String userName;

    @ApiModelProperty(value = "Email")
    private String email;

    @ApiModelProperty(value = "Senha")
    private String password;

    public LoginTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
