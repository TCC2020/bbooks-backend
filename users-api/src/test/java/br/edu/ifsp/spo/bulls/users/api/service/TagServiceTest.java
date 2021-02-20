package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.TagRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TagServiceTest {

    @MockBean
    private TagRepository mockTagRepository;

    @MockBean
    private ProfileRepository mockProfileRepository;

    @MockBean
    private UserBooksRepository mockUserBooksRepository;

    @Autowired
    private TagService tagService;

    private Tag tag = new Tag();
    private Profile profile = new Profile();
    private List<Tag> tagsList = new ArrayList<>();
    private UserBooks userBooks = new UserBooks();
    private Tag tagNotFound = new Tag();

    @BeforeEach
    void setUp() {
        profile.setId(1);

        tag.setId(1L);
        tag.setColor("IS821");
        tag.setProfile(profile);
        tag.setName("tag1");
        tag.setBooks(new ArrayList<>());

        Tag tagOtherUserBook = new Tag();
        tagOtherUserBook.setId(23L);
        tagOtherUserBook.setColor("IS821");
        tagOtherUserBook.setProfile(profile);
        tagOtherUserBook.setName("tag1");
        tagOtherUserBook.setBooks(new ArrayList<>());

        tagsList.add(tag);
        tagsList.add(tagOtherUserBook);

        userBooks.setId(1L);
        userBooks.setProfile(profile);
        userBooks.setIdBookGoogle("32");
        tag.getBooks().add(userBooks);

        UserBooks userBooksOtherTag = new UserBooks();
        userBooksOtherTag.setId(2L);
        userBooksOtherTag.setProfile(profile);
        userBooksOtherTag.setIdBookGoogle("32");

        tagOtherUserBook.getBooks().add(userBooksOtherTag);


        tagNotFound.setId(5L);
    }

    @Test
    void shouldSaveTag(){
        Mockito.when(mockTagRepository.save(tag)).thenReturn(tag);
        Tag tagResult = tagService.save(tag);
        assertEquals(tag, tagResult);
    }

    @Test
    void shouldGetByProfile()  {
        Mockito.when(mockProfileRepository.findById(profile.getId())).thenReturn(Optional.of(profile));
        Mockito.when(mockTagRepository.findByProfile(profile)).thenReturn(tagsList);

        List<Tag> tagsResult = tagService.getByProfile(profile.getId());
        assertEquals(tagsList, tagsResult);
    }

    @Test
    void shouldntGetByProfileIfProfileNotFound() {
        Mockito.when(mockProfileRepository.findById(profile.getId())).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
        assertThrows(ResourceNotFoundException.class, () -> tagService.getByProfile(profile.getId()));
    }

    @Test
    void shouldGetTagByBook()  {
        Mockito.when(mockTagRepository.findAll()).thenReturn(tagsList);
        List<Tag> tagResult = tagService.getByIdBook(userBooks.getId());
        assertEquals(1, tagResult.size());
    }

    @Test
    void shouldGetTagById(){
        Mockito.when(mockTagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        Tag tagResult = tagService.getbyId(tag.getId());
        assertNotNull(tagResult);
    }

    @Test
    void shouldntGetTagByIdWhenTagNotFound(){
        Mockito.when(mockTagRepository.findById(tagNotFound.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        assertThrows(ResourceNotFoundException.class, () ->tagService.getbyId(tagNotFound.getId()));
    }

    @Test
    void shouldUpdate(){
        Mockito.when(mockTagRepository.findById(tag.getId())).thenReturn(Optional.ofNullable(tag));
        Mockito.when(mockUserBooksRepository.findById(userBooks.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockTagRepository.save(tag)).thenReturn(tag);

        Tag tagResult =  tagService.update(tag.getId(), tag);

        assertEquals(tag, tagResult);
    }

    @Test
    void shouldntUpdateWhenIdDontMatch() {
        assertThrows(ResourceBadRequestException.class, () -> tagService.update(tagNotFound.getId(), tag));
    }

    @Test
    void shouldntUpdateWhenTagNotFound() {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        assertThrows(ResourceNotFoundException.class, () ->  tagService.update(tag.getId(), tag));
    }

    @Test
    void shouldPutTagOnBook() {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        Mockito.when(mockUserBooksRepository.findById(userBooks.getId())).thenReturn(Optional.of(userBooks));
        Mockito.when(mockTagRepository.save(tag)).thenReturn(tag);

        Tag tagResult =  tagService.tagBook(tag.getId(), userBooks.getId());

        assertEquals(tag, tagResult);
    }

    @Test
    void shouldntPutTagOnBookWhenTagNotFound() {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        assertThrows(ResourceNotFoundException.class, () -> tagService.tagBook(tag.getId(), userBooks.getId()));

    }

    @Test
    void shouldntPutTagOnBookWhenUserBooksNotFound() {
        Mockito.when(mockUserBooksRepository.findById(userBooks.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> tagService.tagBook(tag.getId(), userBooks.getId()));
    }

    @Test
    void shouldUntagOnBook() {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        Mockito.when(mockUserBooksRepository.findById(userBooks.getId())).thenReturn(Optional.of(userBooks));

        HttpStatus status =  tagService.untagBook(tag.getId(), userBooks.getId());

        assertEquals(HttpStatus.ACCEPTED, status);
    }

    @Test
    void shouldntUntagBookWhenTagNotFound() {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        assertThrows(ResourceNotFoundException.class, () -> tagService.untagBook(tag.getId(), userBooks.getId()));
    }

    @Test
    void shouldntUntagBookWhenUserBooksNotFound() {
        Mockito.when(mockUserBooksRepository.findById(userBooks.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        assertThrows(ResourceNotFoundException.class, () -> tagService.untagBook(tag.getId(), userBooks.getId()));
    }

    @Test
    void shouldDeleteTag()  {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        Mockito.when(mockUserBooksRepository.findById(userBooks.getId())).thenReturn(Optional.of(userBooks));

        tagService.delete(tag.getId());
        Mockito.verify(mockTagRepository, Mockito.atLeastOnce()).delete(tag);
    }

    @Test
    void shouldntDeleteTag() {
        Mockito.when(mockTagRepository.findById(tag.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        assertThrows(ResourceNotFoundException.class, () -> tagService.delete(tag.getId()));
    }
}