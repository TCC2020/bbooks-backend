package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthorBeanUtilTest {

    @Autowired
    AuthorBeanUtil beanUtil;

    @Test
    void toAuthor() {
        AuthorTO authorto = new AuthorTO(1, "autor12");

        Author author = beanUtil.toAuthor(authorto);

        assertEquals(authorto.getId(), author.getId());
        assertEquals(authorto.getName(), author.getName());
    }

    @Test
    void toAuthorTo() {
        Author author = new Author(1, "autor123256");
        AuthorTO authorto = beanUtil.toAuthorTo(author);

        assertEquals(author.getId(),authorto.getId());
        assertEquals( author.getName(), authorto.getName());
    }

    @Test
    void testToAuthorToList() {
        List<Author> authors = new ArrayList<Author>( );
        authors.add(new Author("Autor1"));

        List<AuthorTO> authorToList = beanUtil.toAuthorToList(authors);

        for (AuthorTO authorTO: authorToList ) {
            assertEquals(authors.get(0).getId(), authorTO.getId());
            assertEquals(authors.get(0).getName(), authorTO.getName());
        }

    }
}