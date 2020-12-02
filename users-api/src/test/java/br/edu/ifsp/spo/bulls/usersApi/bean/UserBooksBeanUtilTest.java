package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.Status;
import br.edu.ifsp.spo.bulls.usersApi.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBooksBeanUtilTest {

    @Autowired
    UserBooksBeanUtil beanUtil;

    @MockBean
    TagService mockTagService;

    private UserBooks userBooks = new UserBooks();
    private UserBooksTO userBooksTO = new UserBooksTO();
    private UserBooks userBooks2 = new UserBooks();
    private UserBooksTO userBooksTO2 = new UserBooksTO();
    private UserBookUpdateStatusTO userBookUpdateStatusTO = new UserBookUpdateStatusTO();
    private BookCaseTO bookCaseTO = new BookCaseTO();
    private Profile profile = new Profile();
    private Profile profile2 = new Profile();

    private Tag tag;
    private Book book;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        // carregando tag
        tag = new Tag(1L, "nome", profile);

        // carregando book
        book = new Book("1234489-", "lIVRO TESTE", 10, "português", "editora", 3, "livro");
        book.setId(1);

        // carregando o UserBooksTO
        userBooksTO.setId(1L);
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBookGoogle("32");
        userBooksTO.setStatus(Status.EMPRESTADO);
        userBooksTO.getTags().add(tag);

        // carregando o UserBooksTO2
        userBooksTO2.setId(2L);
        userBooksTO2.setProfileId(2);
        userBooksTO2.setIdBookGoogle("32");
        userBooksTO2.setStatus(Status.EMPRESTADO);
        userBooksTO2.getTags().add(tag);

        //carregando profile
        profile.setId(1);
        profile2.setId(2);

        //Carregando o UserBook
        userBooks.setId(1L);
        userBooks.setProfile(profile);
        userBooks.setIdBookGoogle("32");
        userBooks.setStatus(Status.EMPRESTADO);

        //Carregando o UserBook2
        userBooks2.setId(2L);
        userBooks2.setProfile(profile2);
        userBooks2.setIdBookGoogle("32");
        userBooks2.setStatus(Status.EMPRESTADO);


        //carregando userBookUpdateStatus
        userBookUpdateStatusTO.setId(userBooksTO.getId());
        userBookUpdateStatusTO.setStatus(Status.EMPRESTADO.name());
    }


    @Test
    void toDto() {
        Mockito.when(mockTagService.getByIdBook(userBooks.getId())).thenReturn(userBooksTO.getTags());

        UserBooksTO userBooksTO1 = beanUtil.toDto(userBooks);

        assertEquals(userBooksTO, userBooksTO1);
    }

    @Test
    void toDTOUpdate() {

        UserBookUpdateStatusTO userBooksUpdateTO = beanUtil.toDTOUpdate(userBooks);

        assertEquals(userBookUpdateStatusTO, userBooksUpdateTO);
    }

    @Test
    void toDomain() {

        UserBooks userBooks1 = beanUtil.toDomain(userBooksTO);

        userBooks1.setProfile(profile);

        assertEquals(userBooks, userBooks1);

    }

    @Test
    void toDtoSet() {
        Set<UserBooksTO> userBooksListTO = new HashSet<UserBooksTO>();
        Set<UserBooks> userBooksList = new HashSet<UserBooks>();

        userBooksListTO.add(userBooksTO);
        userBooksListTO.add(userBooksTO2);

        userBooksList.add(userBooks);
        userBooksList.add(userBooks2);

        Mockito.when(mockTagService.getByIdBook(userBooks.getId())).thenReturn(userBooksTO.getTags());
        Mockito.when(mockTagService.getByIdBook(userBooks2.getId())).thenReturn(userBooksTO.getTags());

        Set<UserBooksTO> userBooksListTO1 = beanUtil.toDtoSet(userBooksList);

        assertEquals(userBooksListTO, userBooksListTO1);
    }
}