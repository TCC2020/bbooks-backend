package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService service;

    @Test
    void testSave(){
        List<Author> authors = new ArrayList<Author>( );
        Author author = new Author( "Autor2");

        authors.add(author);
        BookTO bookTO = new BookTO("1234489-",
                "lIVRO TESTE",
                10,
                "português",
                "editora",
                Calendar.getInstance(),
                "livro");
        bookTO.setAuthors(authors);
        BookTO result = service.save(bookTO);

        assertEquals(bookTO, result);
    }
}