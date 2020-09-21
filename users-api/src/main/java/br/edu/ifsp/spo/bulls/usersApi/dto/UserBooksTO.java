package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Data
@ApiModel(value = "Objeto de trânsito: Livros do usuário ")
public class UserBooksTO {
    private Long id;
    private String isbn10;
    private String isbn13;
    private String idBook;
    private List<Tag> tags;
    private UserBooks.Status status;
    private LocalDateTime addDate;
    private Book book;
    private int profileId;
}
