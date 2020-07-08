package br.edu.ifsp.spo.bulls.usersApi.dto;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;




@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthorTOTest {

    AuthorTO author = new AuthorTO(1, "nome");

    @Test
    void getId() {
        assertNotNull(author.getId());
    }

    @Test
    void getName() {
        assertNotNull(author.getName());
    }

    @Test
    void setId() {
        author.setId(2);
    }

    @Test
    void setName() {
        author.setName("author");
    }

    @Test
    void testEquals() {
        AuthorTO author0 = new AuthorTO(1, "nome");
        AuthorTO author1 = new AuthorTO(1, "nome");

        assertEquals(author0, author1);
    }

    @Test
    void testHashCode() {
        AuthorTO author0 = new AuthorTO(1, "nome");
        AuthorTO author1 = new AuthorTO("nome");
        author1.setId(1);

        assertEquals(author0.hashCode(), author1.hashCode());
    }

    @Test
    void testToString() {
        AuthorTO author0 = new AuthorTO();
        author0.setId(1);
        author0.setName("nome");

        assertEquals("AuthorTO(id=1, name=nome)", author0.toString());
    }
}