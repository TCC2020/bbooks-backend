package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AuthorTO {

    private int id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    public AuthorTO( String name) {
        this.name = name;
    }

    public AuthorTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorTO() {
    }
}
