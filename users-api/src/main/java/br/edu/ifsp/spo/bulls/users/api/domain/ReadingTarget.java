package br.edu.ifsp.spo.bulls.users.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Objeto de domínio: Meta de leitura")

@Entity
@Table(name = "reading_targets")
public class ReadingTarget {
    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    @ApiModelProperty(value = "Ano da meta de leitura")
    private Integer year;
    @ApiModelProperty(value = "Livros que estão na meta")
    @ManyToMany
    private List<UserBooks> targets;
    @ApiModelProperty(value = "Identificador do perfil que a meta está relacionada")
    private int profileId;
}