package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de trânsito: Cadastro de usuário ")
public class CadastroUserTO {

    private UUID id;
    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private String token;

    private String idToken;

    private String idSocial;

    private Boolean verified;

    private ProfileTO profile;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;


    @Override
    public boolean equals(Object obj) {
        CadastroUserTO other = (CadastroUserTO) obj;
        return Objects.equals(email, other.email) && Objects.equals(password, other.password)
                && Objects.equals(token, other.token) && Objects.equals(userName, other.userName)
                && Objects.equals(verified, other.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, token, userName, verified);
    }



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
