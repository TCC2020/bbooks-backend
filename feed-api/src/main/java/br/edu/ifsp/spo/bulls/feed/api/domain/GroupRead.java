package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de domínio: Grupos de leitura ")
@Entity
public class GroupRead {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "Identificador")
    private UUID id;

    @ApiModelProperty(value = "Nome do grupo")
    @Column(nullable = false, length = 80,unique = true)
    private String name;

    @ApiModelProperty(value = "Descrição do grupo")
    @Column(length = 255)
    private String description;

    @ApiModelProperty(value = "Privacidade do grupo, qualquer pessoa pode entrar ou mediante aprovação")
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ApiModelProperty(value = "Data de criação do grupo")
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }

}
