package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SurveyOptionsTO {
    private UUID id;
    private String option;
    private int totalVotes;
    private List<VoteTO> votes;
}
