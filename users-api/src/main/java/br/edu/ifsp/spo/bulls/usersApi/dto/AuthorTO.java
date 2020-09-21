package br.edu.ifsp.spo.bulls.usersApi.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Objeto de trânsito: Autor ")
public class AuthorTO {

    private int id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    public AuthorTO( String name) {
        this.name = name;
    }

    public AuthorTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorTO() {
    }
}
