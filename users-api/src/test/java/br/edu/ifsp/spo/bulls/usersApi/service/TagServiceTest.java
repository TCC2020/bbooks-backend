package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TagServiceTest {

    @MockBean
    TagRepository mockTagRepository;

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

        tagsList.add(tag);

        userBooks.setId(1L);
        userBooks.setProfile(profile);
        userBooks.setIdBookGoogle("32");


        tagNotFound.setId(5L);
    }

    @Test
    void should_save_tag() throws Exception {
        fail();
    }

    @Test
    void should_get_by_profile() throws Exception {
        fail();
    }

    @Test
    void shouldnt_get_by_profile_if_profile_not_found() throws Exception {
        fail();
    }

    @Test
    void should_get_tag_by_book() throws Exception {
        fail();
    }

    @Test
    void should_get_tag_by_id() throws Exception {
        fail();
    }

    @Test
    void shouldnt_get_tag_by_id_when_tag_not_found() throws Exception {
        fail();
    }

    @Test
    void should_update() throws Exception {
        fail();
    }

    @Test
    void shouldnt_update_when_id_dont_match() throws Exception {
        fail();
    }

    @Test
    void shouldnt_update_when_tag_not_found() throws Exception {
        fail();
    }

    @Test
    void should_put_tag_on_book() {
        fail();
    }

    @Test
    void shouldnt_put_tag_on_book_when_tag_not_found() {
        fail();
    }

    @Test
    void shouldnt_put_tag_on_book_when_userBooks_not_found() {
        fail();
    }

    @Test
    void should_untag_on_book() {
        fail();
    }

    @Test
    void shouldnt_untag_book_when_tag_not_found() {
        fail();
    }

    @Test
    void shouldnt_untag_book_when_userBooks_not_found() {
        fail();
    }

    @Test
    void should_delete_tag() throws Exception {
        fail();
    }

    @Test
    void shouldnt_delete_tag() throws Exception {
        fail();
    }
}