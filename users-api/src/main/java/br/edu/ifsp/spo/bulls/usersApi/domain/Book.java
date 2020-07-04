package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
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

    private Calendar publishedDate;

    private String edition;

    private String printType;

    @Column(nullable = false)
    @NotBlank(message = "Description is mandatory")
    private String description;

    public Book(@NotBlank(message = "Title is mandatory") String title,
                @NotNull(message = "numberPage is mandatory") int numberPage,
                @NotBlank(message = "Language is mandatory") String language,
                @NotBlank(message = "Publisher is mandatory") String publisher,
                @NotBlank(message = "Published Date is mandatory") Calendar publishedDate,
                @NotBlank(message = "Description is mandatory") String description) {
        this.title = title;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public Book() {
    }

    public Book(@NotBlank(message = "Title is mandatory") String title,
                List<Author> authors,
                @NotNull(message = "numberPage is mandatory") int numberPage,
                @NotBlank(message = "Language is mandatory") String language,
                @NotBlank(message = "Publisher is mandatory") String publisher,
                String category,
                @NotBlank(message = "Published Date is mandatory")Calendar publishedDate,
                String edition, String printType,
                @NotBlank(message = "Description is mandatory") String description) {
        this.title = title;
        this.authors = authors;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.category = category;
        this.publishedDate = publishedDate;
        this.edition = edition;
        this.printType = printType;
        this.description = description;
    }

    public Book( @NotBlank(message = "Title is mandatory") String title,
                 List<Author> authors,
                 @NotNull(message = "NumberPage is mandatory") int numberPage,
                 @NotBlank(message = "Language is mandatory") String language,
                 @NotBlank(message = "Publisher is mandatory") String publisher,
                 @NotBlank(message = "Published Date is mandatory")Calendar publishedDate,
                 @NotBlank(message = "Description is mandatory") String description) {
        this.title = title;
        this.authors = authors;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public Book(@NotBlank(message = "ISBN10 is mandatory") String isbn10,
                @NotBlank(message = "Title is mandatory") String title,
                List<Author> authors,
                @NotNull(message = "NumberPage is mandatory") int numberPage,
                @NotBlank(message = "Language is mandatory") String language,
                @NotBlank(message = "Publisher is mandatory") String publisher,
                @NotBlank(message = "Published Date is mandatory") Calendar publishedDate,
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
