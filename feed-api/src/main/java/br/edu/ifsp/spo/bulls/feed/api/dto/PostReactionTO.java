package br.edu.ifsp.spo.bulls.feed.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReactionTO {
    private UUID postId;
    private ReactionsTO reactions;

    public PostReactionTO(UUID postId){
        this.postId = postId;
    }
}
