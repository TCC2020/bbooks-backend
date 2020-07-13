package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTOTest {

    @Test
    void testEquals() {
        Calendar data = Calendar.getInstance();
        BookTO bookTo = new BookTO( "123","lIVRO TESTE3", this.listaAutores(), 10, "português", "editora",  data, "livro");
        BookTO bookTo2 = new BookTO( "123","lIVRO TESTE3", this.listaAutores(), 10, "português", "editora",  data, "livro");

        assertEquals(bookTo, bookTo2);
    }

    @Test
    void testNotEquals() {

        BookTO bookTo  = new BookTO( "123","lIVRO TESTE3", 10, "português", "editora",  Calendar.getInstance(), "livro123456 ");
        BookTO bookTo2 = new BookTO( "1234567","livro 2", 10, "português", "editora",  Calendar.getInstance(), "livro");

        assertNotEquals(bookTo, bookTo2);
    }

    @Test
    void testToString() {
        BookTO book = new BookTO();
        book.setId(1);
        book.setTitle("title");
        book.setAuthors(this.listaAutores());


        assertEquals("BookTO(id=1, isbn10=null, title=title, authors=[Author(id=0, name=autor 1), Author(id=0, name=autor 2), Author(id=0, name=autor 3)], numberPage=0, language=null, publisher=null, publishedDate=null, description=null)", book.toString());
    }


    List<Author> listaAutores(){
        List<Author> autores = new ArrayList<Author>();

        autores.add(new Author("autor 1"));
        autores.add(new Author("autor 2"));
        autores.add(new Author("autor 3"));

        return autores;
    }

}