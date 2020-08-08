package br.edu.ifsp.spo.bulls.usersApi.dto;


import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCaseTO {
    private int profileId;
    private Set<UserBooks> books;
}
