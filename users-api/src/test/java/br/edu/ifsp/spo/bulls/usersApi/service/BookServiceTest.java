package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import ch.qos.logback.classic.jmx.MBeanUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService service;

    @Test
    void testSave(){
        List<Author> author = new ArrayList<Author>( );
        author.add(new Author(1, "Autor1"));
        BookTO bookTO = new BookTO("lIVRO TESTE", author, 10, "português", "editora",  LocalDateTime.now(), "livro");

        BookTO result = service.save(bookTO);

        assertEquals(bookTO, result);
    }
}