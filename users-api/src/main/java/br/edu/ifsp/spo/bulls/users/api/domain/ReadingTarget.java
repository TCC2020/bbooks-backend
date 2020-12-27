package br.edu.ifsp.spo.bulls.users.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Objeto de domínio: Meta de leitura")

@Entity
@Table(name = "reading_targets")
public class ReadingTarget {
    @ApiModelProperty(value = "Identificador")
    private UUID id;
    @ApiModelProperty(value = "Ano da meta de leitura")
    private Integer year;
    @ApiModelProperty(value = "Livros que estão na meta")
    private List<UserBooks> targets;
    @ApiModelProperty(value = "Identificador do perfil que a meta está relacionada")
    private Long profileId;
}
