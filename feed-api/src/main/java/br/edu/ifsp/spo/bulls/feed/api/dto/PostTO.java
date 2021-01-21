package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.feed.api.enums.PostPrivacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PostTO {

    private UUID id;

    private int profileId;

    private String description;

    private LocalDateTime creationDate;

    private String image;

    private TypePost tipoPost;

    private List<PostTO> comments  ;

    private PostPrivacy privacy;

    public PostTO(UUID id, int profileId, String description, LocalDateTime creationDate, String image, TypePost tipoPost, PostPrivacy privacy) {
        this.id = id;
        this.profileId = profileId;
        this.description = description;
        this.creationDate = creationDate;
        this.image = image;
        this.tipoPost = tipoPost;
        this.privacy = privacy;
    }

    public PostTO() {
    }
}
