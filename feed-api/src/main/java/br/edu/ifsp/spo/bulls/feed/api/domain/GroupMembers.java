package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.common.api.enums.Cargo;
import br.edu.ifsp.spo.bulls.feed.api.enums.MemberStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de domínio: Membros dos grupos de leitura ")
@Entity
public class GroupMembers {

    @ApiModelProperty(value = "Id do grupo")
    @ManyToMany
    private Group group;

    @ApiModelProperty(value = "Id do usuário")
    private UUID userId;

    @ApiModelProperty(value = "Data que o membro entrou no grupo")
    private LocalDateTime date;

    @ApiModelProperty(value = "Função do membro")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @ApiModelProperty(value = "Status do membro")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }

}
