package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.bean.TagBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.TagTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBooksServiceTest {
    @MockBean
    private UserBooksRepository mockUserBooksRepository;
    @MockBean
    private BookRepository mockBookRepository;
    @MockBean
    private ProfileRepository mockProfileRepository;
    @MockBean
    private TagService mockTagService;
    @MockBean
    private TagBeanUtil mockBean;

    @Autowired
    private UserBooksService userBookService;

    private UserBooks userBooks = new UserBooks();
    private UserBooks userBooksLivro = new UserBooks();
    private UserBooksTO userBooksTO = new UserBooksTO();
    private UserBooksTO userBooksTOLivro = new UserBooksTO();
    private BookCaseTO bookCaseTO = new BookCaseTO();
    private Profile profile = new Profile();
    private Set<UserBooks> userBooksList = new HashSet<>();
    private Set<UserBooks> userBooksListDiferente = new HashSet<>();
    private Tag tag;
    private TagTO tagTO;
    private Book book;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        // carregando tag
        tagTO = new TagTO(1L, "nome", profile);
        tag = new Tag(1L, "nome", profile);

        // carregando book
        book = new Book("1234489-", "lIVRO TESTE", 10, "português", "editora", 3, "livro");
        book.setId(1);

        // carregando o UserBooksTO
        userBooksTOLivro.setId(2L);
        userBooksTOLivro.setProfileId(1);
        userBooksTOLivro.setIdBook(1);
        userBooksTOLivro.setStatus(Status.EMPRESTADO);
        userBooksTOLivro.getTags().add(tagTO);

        // carregando o UserBooksTO
        userBooksTO.setId(1L);
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBookGoogle("32");
        userBooksTO.setStatus(Status.EMPRESTADO);
        userBooksTO.getTags().add(tagTO);

        //carregando profile
        profile.setId(1);

        //Carregando o UserBook
        userBooks.setId(1L);
        userBooks.setProfile(profile);
        userBooks.setIdBookGoogle("32");
        userBooks.setStatus(Status.EMPRESTADO);


        // carregando o UserBooksTOLivro
        userBooksLivro.setId(2L);
        userBooksLivro.setProfile(profile);
        userBooksLivro.setBook(book);
        userBooksLivro.setStatus(Status.EMPRESTADO);

        //Carregando o BookCaseTO
        Set<UserBooksTO> userBooksListTO = new HashSet<>();
        userBooksListTO.add(userBooksTO);
        bookCaseTO.setProfileId(1);
        bookCaseTO.setBooks(userBooksListTO);

        //carregando userBooksList com userBook igual
        userBooksList.add(userBooks);


        //carregando userBooksList com userBook diferente
        userBooksListDiferente.add(userBooksLivro);

    }

    @Test
    public void userBooks_service_should_save() {
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockUserBooksRepository.save(userBooks)).thenReturn(userBooks);
        Mockito.when(mockTagService.tagBook(1L, userBooksTO.getId())).thenReturn(tag);
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        Mockito.when(mockUserBooksRepository.findByProfile(profile)).thenReturn(userBooksListDiferente);
        UserBooksTO userBooksTO1 = userBookService.save(userBooksTO);

        assertEquals(userBooksTO, userBooksTO1);
    }

    @Test
    public void user_books_service_shouldnt_save_if_profile_not_found() {

        Mockito.when(mockProfileRepository.findById(1)).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.save(userBooksTO));

    }

    @Test
    public void user_books_service_shouldnt_save_if_userbooks_already_in_bookcase() {
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockTagService.tagBook(1L, userBooksTO.getId())).thenReturn(tag);
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());

        Mockito.when(mockUserBooksRepository.findByProfile(profile)).thenReturn(userBooksList);
        assertThrows(ResourceConflictException.class, () -> userBookService.save(userBooksTO));
    }

    @Test
    public void user_books_service_shouldnt_save_if_book_not_found() {
        Mockito.when(mockBookRepository.findById(1)).thenThrow(new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.save(userBooksTOLivro));
    }

    @Test
    public void user_books_service_should_save_with_book() {
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockUserBooksRepository.save(userBooksLivro)).thenReturn(userBooksLivro);
        Mockito.when(mockTagService.tagBook(tagTO.getId(), userBooksTOLivro.getId())).thenReturn(tag);
        Mockito.when(mockBookRepository.findById(userBooksTOLivro.getIdBook())).thenReturn(Optional.of(book));
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());

        UserBooksTO userBooksTO1 = userBookService.save(userBooksTOLivro);

        assertEquals(userBooksTOLivro, userBooksTO1);

    }

    @Test
    public void user_books_service_should_return_by_profileId(){

        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.of(profile));
        Mockito.when(mockUserBooksRepository.findByProfile(profile)).thenReturn(userBooksList);
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        BookCaseTO result = userBookService.getByProfileId(profile.getId());

        assertEquals(bookCaseTO, result);
    }

    @Test
    public void user_books_service_should_return_by_id(){

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        UserBooksTO alterado =  userBookService.getById(userBooksTO.getId());
        assertEquals(userBooksTO, alterado);
    }

    @Test
    public void user_books_service_shouldnt_return_by_id_when_userBooks_not_found(){

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> userBookService.getById(userBooksTO.getId()));
    }

    @Test
    public void user_books_service_shouldnt_return_by_profileID_if_profile_not_found() {

        Mockito.when(mockProfileRepository.findById(1)).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.getByProfileId(profile.getId()));

    }

    @Test
    public void user_books_should_delete(){
        Mockito.doNothing().when(mockUserBooksRepository).deleteById(userBooksTO.getId());

        userBookService.deleteById(userBooksTO.getId());
        verify(mockUserBooksRepository).deleteById(userBooksTO.getId());
    }

    @Test
    public void user_books_should_update(){
        UserBooksTO novoStatus = userBooksTO;
        novoStatus.setStatus(Status.QUERO_LER);

        UserBooks userBookNovoStatus = userBooks;
        userBookNovoStatus.setStatus(Status.QUERO_LER);

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockUserBooksRepository.save(userBookNovoStatus)).thenReturn(userBookNovoStatus);
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.of(profile));

        UserBooksTO alterado =  userBookService.update(novoStatus);
        assertEquals(novoStatus, alterado);
    }

    @Test
    public void user_books_shouldnt_update_when_userBooks_not_found(){
        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> userBookService.update(userBooksTO));
    }

    @Test
    public void user_books_shouldnt_update_when_userBooks_status_null(){
        UserBooksTO semStatus = userBooksTO;
        semStatus.setStatus(null);
        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        assertThrows(ResourceConflictException.class, () -> userBookService.update(semStatus));
    }

    @Test
    public void user_books_should_update_status(){

        UserBookUpdateStatusTO updateStatus = new UserBookUpdateStatusTO();
        updateStatus.setId(userBooksTO.getId());
        updateStatus.setStatus(Status.QUERO_LER.toString());

        UserBooksTO novoStatus = userBooksTO;
        novoStatus.setStatus(Status.QUERO_LER);

        UserBooks userBookNovoStatus = userBooks;
        userBookNovoStatus.setStatus(Status.QUERO_LER);

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockUserBooksRepository.save(userBookNovoStatus)).thenReturn(userBookNovoStatus);
        Mockito.when(mockBean.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());

        UserBooksTO alterado =  userBookService.updateStatus(updateStatus);
        assertEquals(novoStatus, alterado);
    }

    @Test
    public void user_books_shouldnt_update_status_when_userBooks_status_null(){

        UserBookUpdateStatusTO updateStatus = new UserBookUpdateStatusTO();
        updateStatus.setId(userBooksTO.getId());
        updateStatus.setStatus(null);

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        assertThrows(ResourceConflictException.class, () -> userBookService.updateStatus(updateStatus));
    }

    @Test
    public void user_books_shouldnt_update_status_when_userBooks_not_found(){

        UserBookUpdateStatusTO updateStatus = new UserBookUpdateStatusTO();
        updateStatus.setId(userBooksTO.getId());
        updateStatus.setStatus(Status.LENDO.toString());

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> userBookService.updateStatus(updateStatus));
    }
}
