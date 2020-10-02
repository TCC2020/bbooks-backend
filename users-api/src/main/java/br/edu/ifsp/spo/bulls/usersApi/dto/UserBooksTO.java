package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Data
@ApiModel(value = "Objeto de trânsito: Livros do usuário ")
public class UserBooksTO {
    @ApiModelProperty(value = "Identificador")
    private Long id;
    @ApiModelProperty(value = "Número identificador do livro da Api do google")
    private String idBook;
    @ApiModelProperty(value = "Lista de tags que o livro possui")
    private List<Tag> tags;
    @ApiModelProperty(value = "Status do livro")
    private UserBooks.Status status;
    @ApiModelProperty(value = "Data que o livro foi adicionado na estante")
    private LocalDateTime addDate;
    @ApiModelProperty(value = "Livro")
    private Book book;
    @ApiModelProperty(value = "Usuario dono da estante virtual")
    private int profileId;
}
