package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.common.api.dto.profile.BaseProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.ReactionType;
import lombok.Data;

import java.util.List;

@Data
public class ReactionsByType {
    private ReactionType type;
    private List<BaseProfileTO> profiles;
    private Integer count;
}
