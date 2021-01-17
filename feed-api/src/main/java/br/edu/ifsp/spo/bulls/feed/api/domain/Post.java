package br.edu.ifsp.spo.bulls.feed.api.domain;

import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import com.google.api.client.util.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
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
    private String descricao;

    private DateTime dataPublicacao;

    @ApiModelProperty(value = "Link da imagem")
    private String image;

    @Column(nullable = false)
    private TypePost tipoPost;

    @Column(length = 32)
    private String comentarioId ;
}
