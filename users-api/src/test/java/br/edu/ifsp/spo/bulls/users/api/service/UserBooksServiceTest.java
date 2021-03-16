package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksDataStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.dto.TagTO;
import br.edu.ifsp.spo.bulls.users.api.bean.TagBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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
    private TagBeanUtil mockTagBeanUtil;

    @Autowired
    private UserBooksService userBookService;

    private UserBooks userBooks = new UserBooks();
    private UserBooks userBooksLivro = new UserBooks();
    private UserBooksTO userBooksTO = new UserBooksTO();
    private UserBooksTO userBooksTOLivro = new UserBooksTO();
    private BookCaseTO bookCaseTO = new BookCaseTO();
    private Profile profile = new Profile();
    private Set<UserBooks> userBooksSet = new HashSet<>();
    private List<UserBooks> userBooksListLivro = new ArrayList<>();
    private List<UserBooks> userBooksList = new ArrayList<>();
    private Set<UserBooks> userBooksListDiferente = new HashSet<>();
    private Tag tag;
    private TagTO tagTO;
    private Book book;
    private List<Tag> tagsList = new ArrayList<>();
    private UserBooksDataStatusTO dataBook = new UserBooksDataStatusTO();
    private UserBooksDataStatusTO dataGoogleBook = new UserBooksDataStatusTO();

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
        userBooksTOLivro.setId(1L);
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
        userBooksLivro.setId(1L);
        userBooksLivro.setProfile(profile);
        userBooksLivro.setBook(book);
        userBooksLivro.setStatus(Status.EMPRESTADO);

        //Carregando o BookCaseTO
        Set<UserBooksTO> userBooksListTO = new HashSet<>();
        userBooksListTO.add(userBooksTO);
        bookCaseTO.setProfileId(1);
        bookCaseTO.setBooks(userBooksListTO);

        //carregando userBooksList com userBook igual
        userBooksSet.add(userBooks);

        userBooksListLivro.add(userBooksLivro);
        userBooksList.add(userBooks);


        //carregando userBooksList com userBook diferente
        userBooksListDiferente.add(userBooksLivro);

        tagsList.add(tag);

        dataBook.setBookId(book.getId());
        dataBook.setGoogleId(null);
        dataBook.setEmprestado(1L);
        dataBook.setLido(0L);
        dataBook.setRelendo(0L);
        dataBook.setQueroLer(0L);
        dataBook.setInterrompido(0L);
        dataBook.setLendo(0L);

        dataGoogleBook.setGoogleId(userBooks.getIdBookGoogle());
        dataGoogleBook.setEmprestado(1L);
        dataGoogleBook.setLido(0L);
        dataGoogleBook.setRelendo(0L);
        dataGoogleBook.setQueroLer(0L);
        dataGoogleBook.setInterrompido(0L);
        dataGoogleBook.setLendo(0L);

    }

    @Test
    public void userBooksServiceShouldSave() {
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockUserBooksRepository.save(userBooks)).thenReturn(userBooks);
        Mockito.when(mockTagService.tagBook(1L, userBooksTO.getId())).thenReturn(tag);
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        Mockito.when(mockUserBooksRepository.findByProfile(profile)).thenReturn(userBooksListDiferente);
        UserBooksTO userBooksTO1 = userBookService.save(userBooksTO);

        assertEquals(userBooksTO, userBooksTO1);
    }

    @Test
    public void userBooksServiceShouldntSaveIfProfileNotFound() {

        Mockito.when(mockProfileRepository.findById(1)).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.save(userBooksTO));

    }

    @Test
    public void userBooksServiceShouldntSaveIfUserbooksAlreadyInBookcase() {
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockTagService.tagBook(1L, userBooksTO.getId())).thenReturn(tag);
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());

        Mockito.when(mockUserBooksRepository.findByProfile(profile)).thenReturn(userBooksSet);
        assertThrows(ResourceConflictException.class, () -> userBookService.save(userBooksTO));
    }

    @Test
    public void userBooksServiceShouldntSaveIfBookNotFound() {
        Mockito.when(mockBookRepository.findById(1)).thenThrow(new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.save(userBooksTOLivro));
    }

    @Test
    public void userBooksServiceShouldSaveWithBook() {
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.ofNullable(profile));
        Mockito.when(mockUserBooksRepository.save(userBooksLivro)).thenReturn(userBooksLivro);
        Mockito.when(mockTagService.tagBook(tagTO.getId(), userBooksTOLivro.getId())).thenReturn(tag);
        Mockito.when(mockBookRepository.findById(userBooksTOLivro.getIdBook())).thenReturn(Optional.of(book));
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());

        UserBooksTO userBooksTO1 = userBookService.save(userBooksTOLivro);

        assertEquals(userBooksTOLivro, userBooksTO1);

    }

    @Test
    public void userBooksServiceShouldReturnByProfileId(){

        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.of(profile));
        Mockito.when(mockUserBooksRepository.findByProfile(profile)).thenReturn(userBooksSet);
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        BookCaseTO result = userBookService.getByProfileId(profile.getId(), false);

        assertEquals(bookCaseTO, result);
    }

    @Test
    public void userBooksServiceShouldReturnByProfileIdTimeLine(){

        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.of(profile));
        Mockito.when(mockUserBooksRepository.findByProfileOrderByFinishDateDesc(profile.getId())).thenReturn(userBooksSet);
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        BookCaseTO result = userBookService.getByProfileId(profile.getId(), true);

        assertEquals(bookCaseTO, result);
    }

    @Test
    public void userBooksServiceShouldReturnById(){

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());
        UserBooksTO alterado =  userBookService.getById(userBooksTO.getId());
        assertEquals(userBooksTO, alterado);
    }

    @Test
    public void userBooksServiceShouldntReturnByIdWhenUserBooksNotFound(){

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> userBookService.getById(userBooksTO.getId()));
    }

    @Test
    public void userBooksServiceShouldntReturnByProfileIDIfProfileNotFound() {

        Mockito.when(mockProfileRepository.findById(1)).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.getByProfileId(profile.getId(), false));

    }

    @Test
    public void userBooksShouldDelete(){
        Mockito.doNothing().when(mockUserBooksRepository).deleteById(userBooksTO.getId());

        userBookService.delete(userBooksTO.getId());
        verify(mockUserBooksRepository).deleteById(userBooksTO.getId());
    }

    @Test
    public void userBooksShouldUpdate(){
        UserBooksTO novoStatus = userBooksTO;
        novoStatus.setStatus(Status.QUERO_LER);
        novoStatus.setFinishDate(LocalDateTime.now().minusDays(1));

        UserBooks userBookNovoStatus = userBooks;
        userBookNovoStatus.setStatus(Status.LIDO);
        userBookNovoStatus.setFinishDate(novoStatus.getFinishDate());

        List<TagTO> tagsToList = new ArrayList<>();
        tagsToList.add(tagTO);

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockUserBooksRepository.save(userBookNovoStatus)).thenReturn(userBookNovoStatus);
        Mockito.when(mockTagService.getByIdBook(novoStatus.getId())).thenReturn(tagsList);
        Mockito.when(mockProfileRepository.findById(1)).thenReturn(Optional.of(profile));
        Mockito.when(mockTagService.untagBook(tagTO.getId(), userBookNovoStatus.getId())).thenReturn(HttpStatus.ACCEPTED);
        Mockito.when(mockTagBeanUtil.toDtoList(tagsList)).thenReturn(tagsToList);

        UserBooksTO alterado =  userBookService.update(novoStatus, novoStatus.getId());
        assertEquals(novoStatus, alterado);
    }

    @Test
    public void userBooksShouldntUpdateWhenIdsNotMacth(){

        assertThrows(ResourceConflictException.class, () -> userBookService.update(userBooksTO, new Random().nextLong()));
    }


    @Test
    public void userbooksShouldntUpdateStatusWhenUserBooksNotFound(){
        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> userBookService.update(userBooksTO, userBooksTO.getId()));
    }

    @Test
    public void userBooksShouldntUpdateStatusWhenUserBooksNull(){
        UserBooksTO semStatus = userBooksTO;
        semStatus.setStatus(null);
        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        assertThrows(ResourceConflictException.class, () -> userBookService.update(semStatus, semStatus.getId()));
    }

    @Test
    public void userBooksShouldUpdateStatus(){

        UserBookUpdateStatusTO updateStatus = new UserBookUpdateStatusTO();
        updateStatus.setId(userBooksTO.getId());
        updateStatus.setStatus(Status.QUERO_LER.toString());

        UserBooksTO novoStatus = userBooksTO;
        novoStatus.setStatus(Status.QUERO_LER);

        UserBooks userBookNovoStatus = userBooks;
        userBookNovoStatus.setStatus(Status.QUERO_LER);

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockUserBooksRepository.save(userBookNovoStatus)).thenReturn(userBookNovoStatus);
        Mockito.when(mockTagBeanUtil.toDtoList(mockTagService.getByIdBook(userBooksTO.getId()))).thenReturn(userBooksTO.getTags());

        UserBooksTO alterado =  userBookService.updateStatus(updateStatus);
        assertEquals(novoStatus, alterado);
    }

    @Test
    public void userBooksShouldntUpdateStatusWhenUserBooksStatusNull(){

        UserBookUpdateStatusTO updateStatus = new UserBookUpdateStatusTO();
        updateStatus.setId(userBooksTO.getId());
        updateStatus.setStatus(null);

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenReturn(Optional.of(userBooks));
        assertThrows(ResourceConflictException.class, () -> userBookService.updateStatus(updateStatus));
    }

    @Test
    public void userBooksShouldntUpdateStatusWhenUserBooksNotFound(){

        UserBookUpdateStatusTO updateStatus = new UserBookUpdateStatusTO();
        updateStatus.setId(userBooksTO.getId());
        updateStatus.setStatus(Status.LENDO.toString());

        Mockito.when(mockUserBooksRepository.findById(userBooksTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> userBookService.updateStatus(updateStatus));
    }


    @Test
    public void shouldGetDataInfoForBookWithId() {
        Mockito.when(mockBookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));
        Mockito.when(mockUserBooksRepository.findByBook(book)).thenReturn(userBooksListLivro);

        UserBooksDataStatusTO result = userBookService.getStatusData(null, book.getId());

        assertEquals(dataBook, result);

    }

    @Test
    public void shouldntGetDataInfoForBookWithIdWhenBookNotFound() {
        Mockito.when(mockBookRepository.findById(book.getId()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () -> userBookService.getStatusData(null,book.getId()));

    }

    @Test
    public void shouldntGetDataInfoForGoogleBookWithId() {
        Mockito.when(mockUserBooksRepository.findByIdBookGoogle(userBooks.getIdBookGoogle())).thenReturn(userBooksList);

        UserBooksDataStatusTO result = userBookService.getStatusData(userBooks.getIdBookGoogle(), 0);

        assertEquals(dataGoogleBook, result);
    }
}
