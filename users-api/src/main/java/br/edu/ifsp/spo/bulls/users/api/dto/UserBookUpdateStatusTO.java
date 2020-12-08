package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Objeto de trânsito: Trocar status do livro na estante ")
public class UserBookUpdateStatusTO {
    @ApiModelProperty(value = "Identificador")
    private Long id;
    @ApiModelProperty(value = "Status do livro")
    private String status;
}
