package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class BookTO {

    private int id;

    private String isbn10;

    @NotBlank(message = "Title is mandatory")
    private String title;

    //@NotBlank(message = "Author is mandatory")
    private List<Author> authors;

    private int numberPage;

    @NotBlank(message = "Language is mandatory")
    private String language;

    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    private LocalDateTime publishedDate;

    @NotBlank(message = "Description is mandatory")
    private String description;

    public BookTO(@NotBlank(message = "Title is mandatory") String title,
                  @NotBlank(message = "Author is mandatory") int numberPage,
                  @NotBlank(message = "Language is mandatory") String language,
                  @NotBlank(message = "Publisher is mandatory") String publisher,
                  @NotBlank(message = "Published Date is mandatory") LocalDateTime publishedDate,
                  @NotBlank(message = "Description is mandatory") String description) {
        this.title = title;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public BookTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookTO bookTO = (BookTO) o;

        if (numberPage != bookTO.numberPage) return false;
        if (isbn10 != null ? !isbn10.equals(bookTO.isbn10) : bookTO.isbn10 != null) return false;
        if (title != null ? !title.equals(bookTO.title) : bookTO.title != null) return false;
        if (authors != null ? !authors.equals(bookTO.authors) : bookTO.authors != null) return false;
        if (language != null ? !language.equals(bookTO.language) : bookTO.language != null) return false;
        if (publisher != null ? !publisher.equals(bookTO.publisher) : bookTO.publisher != null) return false;
        if (publishedDate != null ? !publishedDate.equals(bookTO.publishedDate) : bookTO.publishedDate != null)
            return false;
        return description != null ? description.equals(bookTO.description) : bookTO.description == null;
    }

    public BookTO( @NotBlank(message = "Title is mandatory") String title, List<Author> authors,
                  int numberPage, @NotBlank(message = "Language is mandatory") String language,
                  @NotBlank(message = "Publisher is mandatory") String publisher, LocalDateTime publishedDate,
                  @NotBlank(message = "Description is mandatory") String description) {
        this.title = title;
        this.authors = authors;
        this.numberPage = numberPage;
        this.language = language;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
    }
}
