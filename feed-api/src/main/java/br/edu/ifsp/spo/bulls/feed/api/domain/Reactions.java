package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.common.api.enums.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public int profileId;


    @Enumerated(EnumType.STRING)
    public ReactionType reaction;

    public Reactions(int profileId, ReactionType reaction) {
        this.profileId = profileId;
        this.reaction = reaction;
    }

}
