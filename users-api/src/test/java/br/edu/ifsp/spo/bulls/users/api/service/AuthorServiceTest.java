package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.domain.Author;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthorServiceTest {

    @MockBean
    private AuthorRepository mockAuthorRepository;

    @Autowired
    private AuthorService service;

    private Author author;
    private List<Author> authorList;

    @BeforeEach
    public void setUp(){
        author = new Author("testeSave1Author");
        author.setId(1);

        authorList = new ArrayList<>();
        authorList.add(author);
    }

    @Test
    void testSave(){
        Mockito.when(mockAuthorRepository.save(author)).thenReturn(author);
        Author resultado = service.save(author);

        assertEquals(author, resultado);
    }

    @Test
    void testAuthorExist(){
        Mockito.when(mockAuthorRepository.existsByName(author.getName().toLowerCase()))
                .thenReturn(true);

        ResourceConflictException exception = assertThrows(ResourceConflictException.class, ()-> {
            service.save(author);}
        );
        assertEquals(CodeException.AU002.getText(), exception.getMessage());
    }

    @Test
    void testGetOne(){
        Mockito.when(mockAuthorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
        Author result = service.getOne(author.getId());

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
        Mockito.when(mockAuthorRepository.findAll()).thenReturn(authorList);

        List<Author> authors = service.getAll();

        assertEquals(authorList, authors);
    }

    @Test
    void testDelete(){
        Mockito.when(mockAuthorRepository.existsById(author.getId())).thenReturn(true);
        Mockito.doNothing().when(mockAuthorRepository).deleteById(author.getId());
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
        Mockito.when(mockAuthorRepository.existsByName(author.getName().toLowerCase()))
                .thenReturn(false);
        Mockito.when(mockAuthorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
        Mockito.when(mockAuthorRepository.save(author)).thenReturn(author);

        Author result = service.update(author, author.getId());

        assertEquals(author.getName(), result.getName());

    }

    @Test
    void testUpdateAuthorNotFound(){
        Mockito.when(mockAuthorRepository.existsByName(author.getName().toLowerCase()))
                .thenReturn(false);
        Mockito.when(mockAuthorRepository.findById(author.getId()))
                .thenThrow(new ResourceNotFoundException(CodeException.AU001.getText(), CodeException.AU001));

        assertThrows(ResourceNotFoundException.class, () -> service.update(author, author.getId()));
    }

    @Test
    void testUpdateAuthorExist(){
        Mockito.when(mockAuthorRepository.existsByName(author.getName().toLowerCase()))
                .thenReturn(true);

        ResourceConflictException exception = assertThrows(ResourceConflictException.class, ()-> {
            service.update(author, author.getId());}
        );
        assertEquals(CodeException.AU002.getText(), exception.getMessage());
    }

    @Test
    void testReturnAuthorExists(){
        Mockito.when(mockAuthorRepository.existsByName(author.getName().toLowerCase())).thenReturn(true);
        Mockito.when(mockAuthorRepository.findByName(author.getName().toLowerCase())).thenReturn(Optional.of(author));
        Author resultado = service.returnTheAuthorFromDb(author);

        assertEquals(author, resultado);
    }

    @Test
    void testReturnAuthorNotExists(){
        Mockito.when(mockAuthorRepository.existsByName(author.getName())).thenReturn(false);
        Mockito.when(mockAuthorRepository.save(author)).thenReturn(author);
        Author resultado = service.returnTheAuthorFromDb(author);

        assertNotNull(resultado);
    }

    @Test
    void testGetByName(){
        Mockito.when(mockAuthorRepository.findByName(author.getName().toLowerCase())).thenReturn(Optional.ofNullable(author));
        Author result = service.getByName(author.getName());

        assertEquals(author, result);
    }
}
