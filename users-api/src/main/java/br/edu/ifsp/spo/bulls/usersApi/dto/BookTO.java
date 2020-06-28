package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookTO {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String isbn10;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private List<Author> authors;

    @NotBlank(message = "Author is mandatory")
    private int numberPage;

    @NotBlank(message = "Language is mandatory")
    private String language;

    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    @NotBlank(message = "Published Date is mandatory")
    private LocalDateTime publishedDate;

    @NotBlank(message = "Description is mandatory")
    private String description;
}
