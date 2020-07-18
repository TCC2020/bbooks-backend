package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import lombok.Data;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Data
public class UserBooksTO {
    private Long id;
    private String isbn10;
    private String isbn13;
    private String status;
    private LocalDateTime addDate;
    private Book book;
    private int profileId;
}
