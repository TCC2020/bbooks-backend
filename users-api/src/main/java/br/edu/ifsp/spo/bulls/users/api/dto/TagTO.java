package br.edu.ifsp.spo.bulls.users.api.dto;

import br.edu.ifsp.spo.bulls.common.api.domain.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagTO {
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

    public TagTO(Long id, String name, Profile profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }
}
