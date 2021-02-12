package br.edu.ifsp.spo.bulls.feed.api.dto;

import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private UserTO user;

    @Enumerated(EnumType.STRING)
    private TypePost tipoPost;

    private List<PostTO> comments;

    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    public PostTO(UUID id, int profileId, String description, LocalDateTime creationDate, String image, TypePost tipoPost, Privacy privacy) {
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
