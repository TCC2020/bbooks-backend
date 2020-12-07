package br.edu.ifsp.spo.bulls.usersApi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

@Data
@ApiModel(value = "Objeto de trânsito: Recomendação de livros")
public class BookRecommendationTO {

    @ApiModelProperty(value = "Identificador")
    private UUID id;

    @ApiModelProperty(value = "Identificador do perfil que fez a recomendação")
    private int profileIdSubmitter;

    @ApiModelProperty(value = "Identificador do perfil que recebeu a recomendação")
    private int profileIdReceived;

    @ApiModelProperty(value = "Número identificador do livro da Api do google")
    private String idBookGoogle;

    @ApiModelProperty(value = "Livro")
    private int idBook;
}
