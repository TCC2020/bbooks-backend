package br.edu.ifsp.spo.bulls.common.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.UUID;

@Data
@ApiModel(value = "Objeto de transito: Votos de um competidor ")
public class CompetitionVoteReturnTO {

    private UUID id;

    private int value;

    private CompetitionMemberSaveTO member;

    private ProfileTO profile;
}
