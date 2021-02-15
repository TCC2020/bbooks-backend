package br.edu.ifsp.spo.bulls.competition.api.dto;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de transito: Membros da competição")
public class CompetitionMemberTO {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String story;

    @Column(nullable = false)
    private UUID profileId;

    @Column(nullable = false)
    private Role role;

    private UUID competitionId;

}
