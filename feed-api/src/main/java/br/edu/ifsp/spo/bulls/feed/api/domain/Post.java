package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.feed.api.enums.PostPrivacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import com.google.api.client.util.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: Publicações ")
@Table
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "Identificador")
    private UUID id;

    @JoinTable(name="profile",
            joinColumns={@JoinColumn(name="profile",
                    referencedColumnName="id")})
    private int profileId;

    @Column(length = 32)
    private String description;

    private LocalDateTime creationDate;

    @ApiModelProperty(value = "Link da imagem")
    private String image;

    @Column(nullable = false)
    private TypePost tipoPost;

    private UUID upperPostId;

    @Enumerated(EnumType.STRING)
    private PostPrivacy privacy;

    //TODO: Campo da enquete
    //TODO: Reações

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
