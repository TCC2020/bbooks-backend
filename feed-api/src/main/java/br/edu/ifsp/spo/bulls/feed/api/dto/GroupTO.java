package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GroupTO {

    @ApiModelProperty(value = "Id do grupo")
    private UUID id;

    @ApiModelProperty(value = "Id do usuário")
    private UUID userId;

    @ApiModelProperty(value = "Nome do grupo")
    private String name;

    @ApiModelProperty(value = "Descrição do grupo")
    private String description;

    @ApiModelProperty(value = "Privacidade do grupo, qualquer pessoa pode entrar ou mediante aprovação")
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ApiModelProperty(value = "Data de criação do grupo")
    private LocalDateTime creationDate;
}
