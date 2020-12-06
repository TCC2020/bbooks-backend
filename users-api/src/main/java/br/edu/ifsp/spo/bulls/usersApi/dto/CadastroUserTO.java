package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de trânsito: Cadastro de usuário ")
public class CadastroUserTO {

    @ApiModelProperty(value = "Identificador")
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

    @ApiModelProperty(value = "Indica se o usuário tem o cadastro confirmado")
    private Boolean verified;

    @ApiModelProperty(value = "Indica se o usuário tem o cadastro confirmado")
    private String profileImage;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;

    public CadastroUserTO() {}

    public CadastroUserTO(String userName,String email,String password, String token, Boolean verified) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.verified = verified;
    }

    public CadastroUserTO(String userName,String email,String password, Boolean verified) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public CadastroUserTO(String userName,String email,String password) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public CadastroUserTO(String userName,String email,String password,String name, String lastName) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
    }
}
