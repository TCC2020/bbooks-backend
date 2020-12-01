package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
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
        Author author = service.save(new Author("testeSave1Author"));

        assertNotNull(author);
    }

    @Test
    void testAuthorExist(){
//        service.save(new Author("testeSaveFailAuthor"));
//
//        ResourceConflictException exception = assertThrows(ResourceConflictException.class, ()-> {
//            service.save(new Author("testeSaveFailAuthor"));}
//        );
//        assertEquals(CodeException.AU002.getText(), exception.getMessage());
    }

    @Test
    void testGetOne(){
//        Author author = service.save(new Author("testeGetOne"));
//
//        Author result = service.getOne(author.getId());
//
//        assertEquals(author, result);
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
//        service.save(new Author("testGetAll"));
//
//        List<Author> authors = service.getAll();
//
//        assertNotNull(authors);
    }

    @Test
    void testDelete(){
//        Author author = service.save(new Author("testeDeleteOk"));
//
//        service.delete(author.getId());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()-> {
//            service.getOne(author.getId());}
//        );
//        assertEquals(CodeException.AU001.getText(), exception.getMessage());
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

        Author author = service.save(new Author("testeSave1AuthorAAA"));
        author.setName("Livro1UpdateData");

        Author result = service.update(author, author.getId());

        assertEquals(author.getName(), result.getName());

    }

    @Test
    void testUpdateAuthorExist(){

//        service.save(new Author("Livro1UpdateDataOk"));
//        Author author = service.save(new Author("testeUpdateOk"));
//
//        author.setName("Livro1UpdateDataOk");
//
//        ResourceConflictException exception = assertThrows(ResourceConflictException.class, ()-> {
//            service.update(author, author.getId());}
//        );
//        assertEquals(CodeException.AU002.getText(), exception.getMessage());
    }

    @Test
    void testReturnAuthor(){
//        Author author = service.returnTheAuthorFromDb(new Author("returnFromDb"));
//
//        assertNotNull(author.getId());
    }

    @Test
    void testGetByName(){
//        String nome = "testGetByName";
//        service.save(new Author(nome));
//
//        Author author = service.getByName(nome);
//
//        assertNotNull(author);
    }
}
