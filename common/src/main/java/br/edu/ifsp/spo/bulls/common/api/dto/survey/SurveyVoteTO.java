package br.edu.ifsp.spo.bulls.common.api.dto.survey;

import lombok.Data;

import java.util.UUID;

@Data
public class SurveyVoteTO {
    private UUID surveyOptionId;
    private UUID surveyId;
}
