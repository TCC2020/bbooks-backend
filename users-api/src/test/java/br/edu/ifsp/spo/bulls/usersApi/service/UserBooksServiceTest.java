package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.*;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBooksServiceTest {
    @MockBean
    UserRepository repository;

    @MockBean
    UserBooksRepository userBooksRepository;

    @MockBean
    ProfileRepository profileRepository;

    @MockBean
    UserBooksService mockUserBookService;

    @Autowired
    UserBooksService userBookService;

    @Autowired
    UserBooksBeanUtil userBooksBeanUtil;



    @Test
    public void user_books_service_should_save() {

        UserBooksTO userBooksTO = new UserBooksTO();
        userBooksTO.setId(1L);
        List<Author> authors = new ArrayList<Author>();
        authors.add( new Author("nome"));
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBook("32");
        userBooksTO.setTags( new ArrayList<Tag>());

        Profile profile = new Profile(1, "nome", "sobrenome", "pais",
                "cidade", "estado", "dataNasc", new User("username", "email", "senha"));

        UserBooks userBook = new UserBooks();
        userBook.setId(userBooksTO.getId());
        userBook.setPage(userBooksTO.getPage());


        //Mockito.doReturn(userBook).when(userBooksBeanUtil.toDomain(userBooksTO));

        Mockito.when(userBooksRepository.save(userBook)).thenReturn(userBook);

        UserBooksTO userBooksTO1 = userBookService.save(userBooksTO);

        assertEquals(userBooksTO, userBooksTO1);
    }

    @Test
    public void user_books_service_shouldnt_save_if_profile_not_found() {

        UserBooksTO userBooksTO = new UserBooksTO();
        userBooksTO.setId(1L);
        List<Author> authors = new ArrayList<Author>();
        authors.add( new Author("nome"));
        userBooksTO.setBook(new Book("isbn", "titulo", authors, 12, "PT", "editora", 2020, "descrição"));


        Mockito.when(mockUserBookService.save(userBooksTO)).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> mockUserBookService.save(userBooksTO));
        assertEquals(CodeException.PF001.getText(), e.getMessage());
    }

//    @Test
//    public void getByProfileId(){
//        BookCaseTO result = service.getByProfileId(profile.getId());
//
//        assertTrue(true);
//    }

}
