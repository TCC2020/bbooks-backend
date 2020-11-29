package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.*;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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


    private UserBooks userBooks;
    private UserBooksTO userBooksTO;
    private BookCaseTO bookCaseTO;


    @BeforeEach
    void setUp(){
        // carregando o UserBooksTO
        userBooksTO.setId(1L);
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBookGoogle("32");
        userBooksTO.setTags( new ArrayList<Tag>());


        //Carregando o UserBook
        userBooks.setId(1L);

            //carregando profile
            Profile profile = new Profile();
            profile.setId(1);

        userBooks.setProfile(profile);
        userBooks.setIdBookGoogle("32");


        //Carregando o BookCaseTO
        Set<UserBooksTO> userBooksList = new HashSet<UserBooksTO>();
        userBooksList.add(userBooksTO);
        bookCaseTO.setProfileId(1);
        bookCaseTO.setBooks(userBooksList);
    }

    @Test
    public void user_books_service_should_save() {

        Mockito.when(mockUserBookService.convertToUserBooks(userBooksTO)).thenReturn(userBooks);
        Mockito.when(userBooksRepository.save(userBooks)).thenReturn(userBooks);

        UserBooksTO userBooksTO1 = userBookService.save(userBooksTO);

        assertEquals(userBooksTO, userBooksTO1);
    }

    @Test
    public void user_books_service_shouldnt_save_if_profile_not_found() {

        UserBooksTO userBooksTO = new UserBooksTO();
        userBooksTO.setId(1L);
        List<Author> authors = new ArrayList<Author>();
        authors.add( new Author("nome"));
        userBooksTO.setIdBook(32);

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
