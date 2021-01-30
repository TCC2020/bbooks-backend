package br.edu.ifsp.spo.bulls.feed.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@ApiModel(value = "Objeto de domínio: Grupos de leitura ")
@Table
@Data
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "Identificador")
    private UUID id;

    @JoinTable(name="user",
            joinColumns={@JoinColumn(name="userId",
                    referencedColumnName="id")})
    @Column(nullable = false)
    private UUID userId;

    @ApiModelProperty(value = "Nome do grupo")
    @Column(nullable = false, length = 80,unique = true)
    private String name;

    @ApiModelProperty(value = "Descrição do grupo")
    @Column(nullable = false, length = 255)
    private String description;

    // TODO: Implementar essa privacidade como um enum
    @ApiModelProperty(value = "Privacidade do grupo, qualquer pessoa pode entrar ou mediante aprovação")
    @Column(nullable = false, length = 50)
    private String privacidade;

    @ApiModelProperty(value = "Data de criação do grupo")
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }

}
