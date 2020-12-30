package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.TagTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.service.TagService;
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
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBooksBeanUtilTest {

    @Autowired
    private UserBooksBeanUtil beanUtil;

    @MockBean
    private TagService mockTagService;

    @MockBean
    private ProfileRepository mockProfileRepository;

    @MockBean
    private TagBeanUtil mockBean;

    private UserBooks userBooks = new UserBooks();
    private UserBooksTO userBooksTO = new UserBooksTO();
    private UserBooks userBooks2 = new UserBooks();
    private UserBooksTO userBooksTO2 = new UserBooksTO();
    private UserBookUpdateStatusTO userBookUpdateStatusTO = new UserBookUpdateStatusTO();
    private Profile profile = new Profile();
    private Profile profile2 = new Profile();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        // carregando tag
        TagTO tagTO = new TagTO(1L, "nome", profile);

        // carregando book
        Book book = new Book("1234489-", "lIVRO TESTE", 10, "português", "editora", 3, "livro");
        book.setId(1);

        // carregando o UserBooksTO
        userBooksTO.setId(1L);
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBookGoogle("32");
        userBooksTO.setStatus(Status.EMPRESTADO);
        userBooksTO.getTags().add(tagTO);

        // carregando o UserBooksTO2
        userBooksTO2.setId(2L);
        userBooksTO2.setProfileId(2);
        userBooksTO2.setIdBookGoogle("32");
        userBooksTO2.setStatus(Status.EMPRESTADO);
        userBooksTO2.getTags().add(tagTO);

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
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooks.getId()))).thenReturn(userBooksTO.getTags());

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

        Mockito.when(mockProfileRepository.findById(userBooksTO.getProfileId())).thenReturn(Optional.ofNullable(profile));
        UserBooks userBooks1 = beanUtil.toDomain(userBooksTO);

        userBooks1.setProfile(profile);

        assertEquals(userBooks, userBooks1);

    }

    @Test
    void toDtoSet() {
        Set<UserBooksTO> userBooksListTO = new HashSet<>();
        Set<UserBooks> userBooksList = new HashSet<>();

        userBooksListTO.add(userBooksTO);
        userBooksListTO.add(userBooksTO2);

        userBooksList.add(userBooks);
        userBooksList.add(userBooks2);

        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooks.getId()))).thenReturn(userBooksTO.getTags());
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooks2.getId()))).thenReturn(userBooksTO.getTags());

        Set<UserBooksTO> userBooksListTO1 = beanUtil.toDtoSet(userBooksList);

        assertEquals(userBooksListTO, userBooksListTO1);
    }
}