package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.common.api.enums.ReactionType;
import lombok.Data;

import java.util.UUID;

@Data
public class ReactTO {
    private UUID postId;
    ReactionType reactionType;
}
