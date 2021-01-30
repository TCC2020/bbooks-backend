package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.common.api.enums.Cargo;
import br.edu.ifsp.spo.bulls.feed.api.enums.MemberStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "Objeto de domínio: Membros dos grupos de leitura ")
@Entity
public class GroupMembers {

    @ApiModelProperty(value = "Id do membro")
    @EmbeddedId
    private GroupMemberId id;

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
