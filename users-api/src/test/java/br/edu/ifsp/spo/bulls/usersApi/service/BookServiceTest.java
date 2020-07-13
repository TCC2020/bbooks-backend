package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;
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

    @Test
    void testSaveAuthorsNull(){

        BookTO bookTO = new BookTO("1234489675",
                "lIVRO TESTE",
                10,
                "português",
                "editora",
                Calendar.getInstance(),
                "livro");


        Throwable exception = assertThrows(ResourceBadRequestException.class, ()-> {
            service.save(bookTO);}
        );
        assertEquals("O livro deve ter pelo menos 1 autor", exception.getMessage());

    }

    @Test
    void testGetAll(){
        List<Author> authors = new ArrayList<Author>( );
        Author author = new Author( "Autor2");

        authors.add(author);
        BookTO bookTO = new BookTO("123448",
                "lIVRO TESTE",
                10,
                "português",
                "editora",
                Calendar.getInstance(),
                "livro");
        bookTO.setAuthors(authors);
        BookTO result = service.save(bookTO);

        HashSet<BookTO> books = service.getAll();

        assertFalse(books.isEmpty());
    }

    @Test
    void testGetOne(){
        List<Author> authors = new ArrayList<Author>( );
        Author author = new Author( "Autor2");

        authors.add(author);
        BookTO bookTO = new BookTO("1234489",
                "lIVRO TESTE",
                10,
                "português",
                "editora",
                Calendar.getInstance(),
                "livro");
        bookTO.setAuthors(authors);
        BookTO result = service.save(bookTO);

        BookTO book = service.getOne(result.getId());

        assertNotNull(book);
    }

    @Test
    void delete(){
        List<Author> authors = new ArrayList<Author>( );
        Author author = new Author( "Autor2");

        authors.add(author);
        BookTO bookTO = new BookTO("123448",
                "lIVRO TESTE",
                10,
                "português",
                "editora",
                Calendar.getInstance(),
                "livro");
        bookTO.setAuthors(authors);
        BookTO result = service.save(bookTO);

        service.delete(result.getId());

        Throwable exception = assertThrows(ResourceNotFoundException.class, ()-> {
            service.getOne(result.getId());}
            );
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void deleteBookNotFound(){

        Throwable exception = assertThrows(ResourceNotFoundException.class, ()-> {
            service.delete(new Random().nextInt());}
        );
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void update(){
        List<Author> authors = new ArrayList<Author>( );
        Author author = new Author( "Autor2");

        authors.add(author);
        BookTO bookTO = new BookTO("1267448",
                "lIVRO TESTE",
                10,
                "português",
                "editora",
                Calendar.getInstance(),
                "livro");
        bookTO.setAuthors(authors);
        bookTO.setId(service.save(bookTO).getId());

        bookTO.setTitle("Teste");

        BookTO result = service.update(bookTO);

        assertTrue("Teste".equals(result.getTitle()));


    }
}