package br.edu.ifsp.spo.bulls.competition.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@ApiModel(value = "Objeto de domínio: Votos de um competidor")
public class CompetitionVotes {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    private int value;

    @ManyToOne
    private CompetitionMember member;

    private int profileId;
}
