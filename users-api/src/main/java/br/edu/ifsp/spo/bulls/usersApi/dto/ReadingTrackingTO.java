package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de transito: ACOMPANHAMENTO_LEITURA ")
public class ReadingTrackingTO {

        @Id
        @ApiModelProperty(value = "Identificador")
        private UUID id;

        @ApiModelProperty(value = "Página em que o usuário parou a leitura")
        @Column(nullable = false)
        private int numPag;

        @ApiModelProperty(value = "Comentário")
        private String comentario;

        @ApiModelProperty(value = "Percentual de livro conluído")
        private float percentage;

        @ApiModelProperty(value = "Data do acompanhamento")
        private LocalDateTime creationDate;

        @ApiModelProperty(value = "Grupo de acompanhamentos que esse registro pertence")
        private UUID trackingUpId;

        @PrePersist
        public void prePersist() {
            creationDate = LocalDateTime.now();
        }


}
