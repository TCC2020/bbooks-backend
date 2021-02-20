package br.edu.ifsp.spo.bulls.common.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@ApiModel(value = "Objeto de transito: Votos de um competidor")
public class CompetitionVotesTO {

    private UUID id;

    private int value;

    @ManyToOne
    private CompetitionMemberTO member;

    // TODO: Colocar o profileTO
    private int profileId;
}
