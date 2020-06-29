package br.edu.ifsp.spo.bulls.usersApi.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String isbn10;

    @NotBlank(message = "Title is mandatory")
    private String title;


    @NotBlank(message = "Author is mandatory")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Livro_Autor",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @NotBlank(message = "Author is mandatory")
    private int numberPage;

    @NotBlank(message = "Language is mandatory")
    private String language;

    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    private String category;

    @NotBlank(message = "Published Date is mandatory")
    private LocalDateTime publishedDate;

    private String edition;

    private String printType;

    @NotBlank(message = "Description is mandatory")
    private String description;

    public Book(@NotBlank(message = "Title is mandatory") String title,
                @NotBlank(message = "Author is mandatory") List<Author> authors,
                @NotBlank(message = "Author is mandatory") int numberPage,
                @NotBlank(message = "Language is mandatory") String language,
                @NotBlank(message = "Publisher is mandatory") String publisher,
                @NotBlank(message = "Published Date is mandatory") LocalDateTime publishedDate,
                @NotBlank(message = "Description is mandatory") String description) {
        this.title = title;
        this.authors = authors;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public Book() {
    }
}
