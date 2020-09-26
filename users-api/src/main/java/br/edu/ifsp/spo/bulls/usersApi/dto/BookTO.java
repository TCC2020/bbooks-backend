package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "Objeto de trânsito: Livro ")
public class BookTO {

    @ApiModelProperty(value = "Identificador")
    private int id;

    @ApiModelProperty(value = "Número ISBN10 do livro")
    @NotBlank(message = "ISBN10 is mandatory")
    private String isbn10;

    @ApiModelProperty(value = "Título do livro")
    @NotBlank(message = "Title is mandatory")
    private String title;

    @ApiModelProperty(value = "Lista de autores que escreveram o livro")
    private List<Author> authors;

    @ApiModelProperty(value = "Quantidade de páginas do livro")
    @NotNull(message = "NumberPage is mandatory")
    private int numberPage;

    @ApiModelProperty(value = "Língua")
    @NotBlank(message = "Language is mandatory")
    private String language;

    @ApiModelProperty(value = "Editora")
    @NotBlank(message = "Publisher is mandatory")
    private String publisher;

    @ApiModelProperty(value = "Data de publicação")
    private int publishedDate;

    @ApiModelProperty(value = "Descrição do livro")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @ApiModelProperty(value = "Imagem do livro")
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
