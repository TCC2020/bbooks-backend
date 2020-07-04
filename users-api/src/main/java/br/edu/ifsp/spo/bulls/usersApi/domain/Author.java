package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Book> books ;

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
