package br.edu.ifsp.spo.bulls.feed.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class FeedTO {
    private List<PostTO> posts;
}
