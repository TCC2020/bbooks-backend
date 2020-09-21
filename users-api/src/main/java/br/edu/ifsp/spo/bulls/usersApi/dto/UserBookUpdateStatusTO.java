package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Objeto de trânsito: Trocar status do livro na estante ")
public class UserBookUpdateStatusTO {
    private Long id;
    private String status;
}
