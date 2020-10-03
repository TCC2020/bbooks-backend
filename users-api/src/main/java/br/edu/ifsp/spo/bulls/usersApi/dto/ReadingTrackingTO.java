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
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", updatable = false, unique = true)
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

        @ApiModelProperty(value = "Cadastro do livro na estante do usuário relacionado ao acompmanhamento")
        private UserBooksTO userBook;

        @PrePersist
        public void prePersist() {
            creationDate = LocalDateTime.now();
        }


}
