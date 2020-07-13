package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(nullable = false,
            unique = true)
    @NotBlank(message = "ISBN10 is mandatory")
    private String isbn10;

    @Column(nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Author> authors;

    @Column(nullable = false)
    @NotNull(message = "NumberPage is mandatory")
    private int numberPage;

    @Column(nullable = false)
    @NotBlank(message = "Language is mandatory")
    private String language;

    @Column(nullable = false)
    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    private String category;

    private int publishedDate;

    private String edition;

    private String printType;

    @Column(nullable = false)
    @NotBlank(message = "Description is mandatory")
    private String description;

    public Book() {
    }

    public Book(@NotBlank(message = "ISBN10 is mandatory") String isbn10,
                @NotBlank(message = "Title is mandatory") String title,
                List<Author> authors,
                @NotNull(message = "NumberPage is mandatory") int numberPage,
                @NotBlank(message = "Language is mandatory") String language,
                @NotBlank(message = "Publisher is mandatory") String publisher,
                @NotBlank(message = "Published Date is mandatory") int publishedDate,
                @NotBlank(message = "Description is mandatory") String description) {
        this.isbn10 = isbn10;
        this.title = title;
        this.authors = authors;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }
}
