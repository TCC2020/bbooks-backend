package br.edu.ifsp.spo.bulls.usersApi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: TAG")
@Table(name = "tag")
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


}
