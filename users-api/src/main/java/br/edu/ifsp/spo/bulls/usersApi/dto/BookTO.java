package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BookTO {

    private int id;

    @NotBlank(message = "ISBN10 is mandatory")
    private String isbn10;

    @NotBlank(message = "Title is mandatory")
    private String title;

    private List<Author> authors;

    @NotNull(message = "NumberPage is mandatory")
    private int numberPage;

    @NotBlank(message = "Language is mandatory")
    private String language;

    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    private int publishedDate;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private String image;

    public BookTO() {
    }



    public BookTO(@NotBlank(message = "ISBN10 is mandatory") String isbn10,
                  @NotBlank(message = "Title is mandatory") String title,
                  @NotNull(message = "NumberPage is mandatory") int numberPage,
                  @NotBlank(message = "Language is mandatory") String language,
                  @NotBlank(message = "Publisher is mandatory") String publisher,
                  int publishedDate,
                  @NotBlank(message = "Description is mandatory") String description) {
        this.isbn10 = isbn10;
        this.title = title;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public BookTO(@NotBlank(message = "ISBN10 is mandatory") String isbn10, @NotBlank(message = "Title is mandatory") String title, List<Author> authors, @NotNull(message = "NumberPage is mandatory") int numberPage, @NotBlank(message = "Language is mandatory") String language, @NotBlank(message = "Publisher is mandatory") String publisher, int publishedDate, @NotBlank(message = "Description is mandatory") String description) {
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
