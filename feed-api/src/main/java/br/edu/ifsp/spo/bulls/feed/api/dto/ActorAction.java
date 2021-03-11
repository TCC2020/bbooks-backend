package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.common.api.enums.ReactionType;
import lombok.Data;

@Data
public class ActorAction {
    ReactionType reactionType;

    public ActorAction(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
