package br.edu.ifsp.spo.bulls.users.api.dto;

import br.edu.ifsp.spo.bulls.users.api.domain.Author;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookTOTest {

    @Test
    void testEquals() {
        BookTO bookTo = new BookTO( "123","lIVRO TESTE3", this.listaAutores(),
                10, "português", "editora",  1, "livro");
        BookTO bookTo2 = new BookTO( "123","lIVRO TESTE3", this.listaAutores(),
                10, "português", "editora",  1, "livro");

        assertEquals(bookTo, bookTo2);
    }

    @Test
    void testNotEquals() {

        BookTO bookTo  = new BookTO( "123","lIVRO TESTE3", 10,
                "português", "editora",  2, "livro123456 ");
        BookTO bookTo2 = new BookTO( "1234567","livro 2", 10,
                "português", "editora",  2, "livro");

        assertNotEquals(bookTo, bookTo2);
    }

    @Test
    void testToString() {
        BookTO book = new BookTO();
        book.setId(1);
        book.setTitle("title");
        book.setAuthors(this.listaAutores());


        assertEquals("BookTO(id=1, isbn10=null, title=title, authors=[Author(id=0, name=autor 1), Author(id=0, name=autor 2), Author(id=0, name=autor 3)], numberPage=0, language=null, publisher=null, publishedDate=0, description=null, image=null)", book.toString());
    }


    private List<Author> listaAutores(){
        List<Author> autores = new ArrayList<>();

        autores.add(new Author("autor 1"));
        autores.add(new Author("autor 2"));
        autores.add(new Author("autor 3"));

        return autores;
    }

}