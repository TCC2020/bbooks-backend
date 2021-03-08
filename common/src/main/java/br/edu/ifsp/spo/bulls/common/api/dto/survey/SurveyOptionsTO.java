package br.edu.ifsp.spo.bulls.common.api.dto.survey;

import br.edu.ifsp.spo.bulls.common.api.dto.BookTO;
import br.edu.ifsp.spo.bulls.common.api.dto.VoteTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SurveyOptionsTO {
    private UUID id;
    private String option;
    private String googleBookId;
    private int bootId;
    private BookTO book;
    private List<VoteTO> votes;
}
