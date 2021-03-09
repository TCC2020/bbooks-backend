package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: Publicações ")
@Table
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "Identificador")
    private UUID id;

    @JoinTable(name="profiles",
            joinColumns={@JoinColumn(name="profile_id",
                    referencedColumnName="id")})
    private int profileId;

    @Column(length = 300)
    private String description;

    private LocalDateTime creationDate;

    @ApiModelProperty(value = "Link da imagem")
    private String image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypePost tipoPost;

    private UUID upperPostId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @ManyToOne
    private GroupRead group;



    @OneToMany(cascade = CascadeType.ALL)
    private List<Reactions> reactions;

    //TODO: Reações

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
    }
}
