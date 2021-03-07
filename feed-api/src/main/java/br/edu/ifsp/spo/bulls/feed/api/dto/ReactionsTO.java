package br.edu.ifsp.spo.bulls.feed.api.dto;

import lombok.Data;

@Data
public class ReactionsTO {
    private Integer count;
    private ReactionsByType likes;
    private ReactionsByType dislike;
    private ReactionsByType loved;
    private ReactionsByType hilarius;
    private ReactionsByType surprised;
    private ReactionsByType sad;
    private ReactionsByType hated;

}
