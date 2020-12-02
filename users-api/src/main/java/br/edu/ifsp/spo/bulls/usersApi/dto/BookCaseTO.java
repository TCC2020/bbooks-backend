package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Objeto de trânsito: Estante virtual")
public class BookCaseTO {
    @ApiModelProperty(value = "Identificador do perfil que é dono da estante virtual")
    private int profileId;

    @ApiModelProperty(value = "Lista de livros que compõe esta estante")
    private Set<UserBooksTO> books;
}
