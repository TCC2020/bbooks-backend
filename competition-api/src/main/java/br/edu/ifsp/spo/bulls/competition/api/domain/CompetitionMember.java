package br.edu.ifsp.spo.bulls.competition.api.domain;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Data
@ApiModel(value = "Objeto de domínio: Membros da competição")
public class CompetitionMember {

    private UUID memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String story;

    private UUID profileId;

    private Role role;

    private Competition competition;
}
