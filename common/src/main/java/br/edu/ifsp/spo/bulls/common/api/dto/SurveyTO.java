package br.edu.ifsp.spo.bulls.common.api.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SurveyTO {
    private UUID id;
    private boolean bookMonth;
    private boolean open;
    private List<SurveyOptionsTO> options;
}
