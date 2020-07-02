package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany
    @JoinTable(joinColumns=
            @JoinColumn(name="book_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="author_id", referencedColumnName="id")
    )
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
