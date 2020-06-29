package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookBeanUtilTest {

    @Autowired
    BookBeanUtil beanUtil;

    @Test
    void testToBook(){
        List<Author>  author = new ArrayList<Author>( );
        author.add(new Author(1, "Autor1"));

        BookTO bookTo = new BookTO( "lIVRO TESTE", author, 10, "português", "editora",  LocalDateTime.now(), "livro");

        Book book = beanUtil.toBook(bookTo);

        assertEquals(bookTo.getAuthors(), book.getAuthors());
        assertEquals(bookTo.getDescription(), book.getDescription());
        assertEquals(bookTo.getId(), book.getId());
        assertEquals(bookTo.getIsbn10(), book.getIsbn10());
        assertEquals(bookTo.getLanguage(), book.getLanguage());
        assertEquals(bookTo.getNumberPage(), book.getNumberPage());
        assertEquals(bookTo.getPublishedDate(), book.getPublishedDate());
        assertEquals(bookTo.getPublisher(), book.getPublisher());
        assertEquals(bookTo.getTitle(), book.getTitle());

    }

    @Test
    void testToBookTO(){
        List<Author>  author = new ArrayList<Author>( );
        author.add(new Author(1, "Autor1"));

        Book book = new Book( "lIVRO TESTE", author, 10, "português", "editora",  LocalDateTime.now(), "livro");

        BookTO bookTo = beanUtil.toBookTO(book);

        assertEquals(book.getAuthors(), bookTo.getAuthors());
        assertEquals(book.getDescription(), bookTo.getDescription() );
        assertEquals(book.getId(), bookTo.getId() );
        assertEquals(book.getIsbn10(), bookTo.getIsbn10() );
        assertEquals(book.getLanguage(), bookTo.getLanguage() );
        assertEquals(book.getNumberPage(), bookTo.getNumberPage() );
        assertEquals(book.getPublishedDate(), bookTo.getPublishedDate() );
        assertEquals(bookTo.getPublisher(), book.getPublisher());
        assertEquals(book.getTitle(), bookTo.getTitle() );

    }

    @Test
    void testToBookTOList(){
        List<Author>  author = new ArrayList<Author>( );
        author.add(new Author(1, "Autor1"));

        Book book = new Book( "lIVRO TESTE", author, 10, "português", "editora",  LocalDateTime.now(), "livro");

        HashSet<Book> listBooks = new HashSet<Book>();
        listBooks.add(book);

        HashSet<BookTO> bookToList = beanUtil.toBookTO(listBooks);

        for (BookTO bookTo: bookToList ) {
            assertEquals(book.getAuthors(), bookTo.getAuthors());
            assertEquals(book.getDescription(), bookTo.getDescription());
            assertEquals(book.getId(), bookTo.getId());
            assertEquals(book.getIsbn10(), bookTo.getIsbn10());
            assertEquals(book.getLanguage(), bookTo.getLanguage());
            assertEquals(book.getNumberPage(), bookTo.getNumberPage());
            assertEquals(book.getPublishedDate(), bookTo.getPublishedDate());
            assertEquals(book.getPublisher(), bookTo.getPublisher());
            assertEquals(book.getTitle(), bookTo.getTitle());
        }


    }
}