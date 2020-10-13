package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    AuthorService service;

    @Test
    void testSave(){
        AuthorTO author = service.save(new AuthorTO("testeSave1Author"));

        assertNotNull(author);
    }

    @Test
    void testAuthorExist(){
        service.save(new AuthorTO("testeSaveFailAuthor"));

        ResourceConflictException exception = assertThrows(ResourceConflictException.class, ()-> {
            service.save(new AuthorTO("testeSaveFailAuthor"));}
        );
        assertEquals(CodeException.AU002.getText(), exception.getMessage());
    }

    @Test
    void testGetOne(){
        AuthorTO author = service.save(new AuthorTO("testeGetOne"));

        AuthorTO result = service.getOne(author.getId());

        assertEquals(author, result);
    }

    @Test
    void testGetOneFail(){

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> {
            service.getOne(new Random().nextInt());}
        );
        assertEquals(CodeException.AU001.getText(), exception.getMessage());
    }

    @Test
    void testGetAll(){
        service.save(new AuthorTO("testGetAll"));

        List<AuthorTO> authors = service.getAll();

        assertNotNull(authors);
    }

    @Test
    void testDelete(){
        AuthorTO author = service.save(new AuthorTO("testeDeleteOk"));

        service.delete(author.getId());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> {
            service.getOne(author.getId());}
        );
        assertEquals(CodeException.AU001.getText(), exception.getMessage());
    }

    @Test
    void testDeleteAuthorNotFound(){

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> {
            service.delete(new Random().nextInt());}
        );
        assertEquals(CodeException.AU001.getText(), exception.getMessage());
    }

    @Test
    void testUpdate(){

        AuthorTO author = service.save(new AuthorTO("testeSave1AuthorAAA"));
        author.setName("Livro1UpdateData");

        AuthorTO result = service.update(author, author.getId());

        assertEquals(author.getName(), result.getName());

    }

    @Test
    void testUpdateAuthorExist(){

        service.save(new AuthorTO("Livro1UpdateDataOk"));
        AuthorTO author = service.save(new AuthorTO("testeUpdateOk"));

        author.setName("Livro1UpdateDataOk");

        ResourceConflictException exception = assertThrows(ResourceConflictException.class, ()-> {
            service.update(author, author.getId());}
        );
        assertEquals(CodeException.AU002.getText(), exception.getMessage());
    }

    @Test
    void testReturnAuthor(){
        Author author = service.returnTheAuthorFromDb(new Author("returnFromDb"));

        assertNotNull(author.getId());
    }

    @Test
    void testGetByName(){
        String nome = "testGetByName";
        service.save(new AuthorTO(nome));

        AuthorTO author = service.getByName(nome);

        assertNotNull(author);
    }
}
