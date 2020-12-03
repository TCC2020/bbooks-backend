package br.edu.ifsp.spo.bulls.usersApi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Entity
@ApiModel(value = "Objeto de domínio: Livro")
@Table(name = "books", indexes = { 
		@Index(name = "IDX_BOOK_TITLE", columnList = "title"), 
		@Index(name = "IDX_BOOK_CATEGORY", columnList = "category"),
		@Index(name = "IDX_BOOK_PUBLISHER", columnList = "publisher") })
public class Book {

    @Id
    @ApiModelProperty(value = "Identificador")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(nullable = false,
            unique = true)
    @NotBlank(message = "ISBN10 is mandatory")
    @ApiModelProperty(value = "Número ISBN10 do livro")
    private String isbn10;

    @Column(nullable = false)
    @NotBlank(message = "Title is mandatory")
    @ApiModelProperty(value = "Título do livro")
    private String title;

    @ManyToMany(cascade = CascadeType.MERGE)
    @ApiModelProperty(value = "Lista de autores que escreveram o livro")
    private List<Author> authors;

    @Column(nullable = false)
    @NotNull(message = "NumberPage is mandatory")
    @ApiModelProperty(value = "Quantidade de páginas do livro")
    private int numberPage;

    @Column(nullable = false)
    @NotBlank(message = "Language is mandatory")
    @ApiModelProperty(value = "Língua")
    private String language;

    @Column(nullable = false)
    @NotBlank(message = "Publisher is mandatory")
    @ApiModelProperty(value = "Editora")
    private String publisher;

    @ApiModelProperty(value = "Categoria do livro")
    private String category;

    @ApiModelProperty(value = "Data de publicação")
    private int publishedDate;

    @ApiModelProperty(value = "Edição do livro")
    private String edition;

    @ApiModelProperty(value = "Tipo de impressão")
    private String printType;

    @Column(nullable = false)
    @NotBlank(message = "Description is mandatory")
    @ApiModelProperty(value = "Descrição do livro")
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

    public Book(@NotBlank(message = "ISBN10 is mandatory") String isbn10, @NotBlank(message = "Title is mandatory") String title, @NotNull(message = "NumberPage is mandatory") int numberPage, @NotBlank(message = "Language is mandatory") String language, @NotBlank(message = "Publisher is mandatory") String publisher, int publishedDate, @NotBlank(message = "Description is mandatory") String description) {
        this.isbn10 = isbn10;
        this.title = title;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }
}
