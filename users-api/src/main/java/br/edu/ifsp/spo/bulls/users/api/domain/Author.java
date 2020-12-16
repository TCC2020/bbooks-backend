package br.edu.ifsp.spo.bulls.users.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ApiModel(value = "Objeto de domínio: Autor ")
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "Identificador")
    private int id;

    @Column(unique = true)
    @ApiModelProperty(value = "Nome do autor")
    private String name;

    public Author( String name) {
        this.name = name;
    }

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author() {
    }
}
