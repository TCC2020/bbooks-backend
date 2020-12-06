package br.edu.ifsp.spo.bulls.usersApi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: TAG")
@Table(name = "tag", indexes = {
		@Index(name = "IDX_TAG_NAME", columnList = "name") })
public class Tag {
    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @ApiModelProperty(value = "Usuario dono desta TAG")
    @ManyToOne
    private Profile profile;
    @ApiModelProperty(value = "Cor que representa esta TAG")
    private String color;

    @ApiModelProperty(value = "Lista de userBooks")
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<UserBooks> books;

    public Tag(Long id, String name, Profile profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public Tag() {

    }
}
