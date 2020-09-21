package br.edu.ifsp.spo.bulls.usersApi.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Objeto de trânsito: Autor ")
public class AuthorTO {

    @ApiModelProperty(value = "Identificador")
    private int id;

    @ApiModelProperty(value = "Nome do autor")
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
