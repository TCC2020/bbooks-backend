package br.edu.ifsp.spo.bulls.usersApi.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@ApiModel(value = "Objeto de domínio: Autor ")
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique = true)
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
