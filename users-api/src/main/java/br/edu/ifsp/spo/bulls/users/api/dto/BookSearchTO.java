package br.edu.ifsp.spo.bulls.users.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchTO {
    private Page<BookTO> books;
    private LinkedHashMap<Object, Object> googleBooks;
    private Long page;
    private String search;
}
