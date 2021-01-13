package br.edu.ifsp.spo.bulls.users.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingTargetTO {
    @ApiModelProperty(value = "Identificador")
    private UUID id;
    @ApiModelProperty(value = "Ano da meta de leitura")
    private Integer year;
    @ApiModelProperty(value = "Livros que estão na meta")
    private List<UserBooksTO> targets;
    @ApiModelProperty(value = "Identificador do perfil que a meta está relacionada")
    private int profileId;
    @ApiModelProperty(value = "Progresso da meta")
    private ReadingTargetProgressTO progress;
}
