package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de transito: ACOMPANHAMENTO_LEITURA ")
public class TrackingTO {
    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true)
    private UUID id;

    @ApiModelProperty(value = "Data do inicio acompanhamento")
    private LocalDateTime creationDate;

    @ApiModelProperty(value = "Comentário")
    private String comentario;

    @ApiModelProperty(value = "Cadastro do livro na estante do usuário relacionado ao acompmanhamento")
    private Long userBookId;

    @ApiModelProperty(value = "Data do termino acompanhamento")
    private LocalDateTime finishedDate;

    @ApiModelProperty(value = "Velocidade de Leitura")
    private Double velocidadeLeitura;

    @ApiModelProperty(value = "Lista de acompanhamentos")
    private List<ReadingTrackingTO> trackings;
}
