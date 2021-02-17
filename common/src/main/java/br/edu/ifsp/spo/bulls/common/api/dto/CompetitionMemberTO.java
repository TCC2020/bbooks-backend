package br.edu.ifsp.spo.bulls.common.api.dto;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
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

    private String title;

    private String story;

    @Column(nullable = false)
    private int profileId;

    @Column(nullable = false)
    private Role role;

    private Status status;

    private UUID competitionId;

    private float meanVote;

}
