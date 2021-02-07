package br.edu.ifsp.spo.bulls.common.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
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
    private List<AuthorTO> authors;

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
}
