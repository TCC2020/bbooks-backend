package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.service.TagService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService mockTagService;

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

        when(mockTagService.save(tag)).thenReturn(tag);

        mockMvc.perform(post("/tags")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_by_profile() throws Exception {

        when(mockTagService.getByProfile(profile.getId())).thenReturn(tagsList);

        mockMvc.perform(get("/tags/profile/" + profile.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_get_by_profile_if_profile_not_found() throws Exception {

        when(mockTagService.getByProfile(2)).thenThrow(new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        mockMvc.perform(get("/tags/profile/" + 2)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_tag_by_book() throws Exception {
        when(mockTagService.getByIdBook(userBooks.getId())).thenReturn(tagsList);
        mockMvc.perform(get("/tags/book/" + userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_tag_by_id() throws Exception {

        when(mockTagService.getbyId(tag.getId())).thenReturn(tag);
        mockMvc.perform(get("/tags/" + tag.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_get_tag_by_id_when_tag_not_found() throws Exception {

        when(mockTagService.getbyId(4L)).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        mockMvc.perform(get("/tags/" + 4)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update() throws Exception {
        when(mockTagService.update(tag.getId(), tag)).thenReturn(tag);

        mockMvc.perform(put("/tags/" + tag.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tag)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_update_when_id_dont_match() throws Exception {
        when(mockTagService.update(6L, tagNotFound)).thenThrow(new ResourceBadRequestException(CodeException.TG002.getText(), CodeException.TG002));

        mockMvc.perform(put("/tags/" + 6L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tagNotFound)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldnt_update_when_tag_not_found() throws Exception {
        when(mockTagService.update(5L, tagNotFound)).thenThrow(new ResourceNotFoundException(CodeException.TG002.getText(), CodeException.TG002));

        mockMvc.perform(put("/tags/" + 5)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tagNotFound)))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_put_tag_on_book() throws Exception {
        Mockito.when(mockTagService.tagBook(tag.getId(), userBooks.getId())).thenReturn(tag);

        mockMvc.perform(put("/tags/" + tag.getId() + "/book/" +userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_put_tag_on_book_when_tag_not_found() throws Exception {
        Long tagId = new Random().nextLong();
        Mockito.when(mockTagService.tagBook(tagId, userBooks.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        mockMvc.perform(put("/tags/" + tagId + "/book/" + userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldnt_put_tag_on_book_when_userBooks_not_found() throws Exception {
        Long userBooksId = new Random().nextLong();
        Mockito.when(mockTagService.tagBook(tag.getId(), userBooksId)).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));

        mockMvc.perform(put("/tags/" + tag.getId() + "/book/" + userBooksId )
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_untag_on_book() throws Exception {
        Mockito.when(mockTagService.untagBook(tag.getId(), userBooks.getId())).thenReturn(HttpStatus.ACCEPTED);
        mockMvc.perform(delete("/tags/" + tag.getId() + "/book/" + userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_untag_book_when_tag_not_found() throws Exception {
        Long tagId = new Random().nextLong();
        Mockito.when(mockTagService.untagBook(tagId, userBooks.getId())).thenThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        mockMvc.perform(delete("/tags/" + tagId+ "/book/" + userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldnt_untag_book_when_userBooks_not_found() throws Exception {
        Long userBooksId = new Random().nextLong();
        Mockito.when(mockTagService.untagBook(tag.getId(), userBooksId)).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        mockMvc.perform(delete("/tags/" + tag.getId() + "/book/" + userBooksId)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_tag() throws Exception {
        Mockito.doNothing().when(mockTagService).delete(tag.getId());
        mockMvc.perform(delete("/tags/" + tag.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_delete_tag() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001)).when(mockTagService).delete(2L);
        mockMvc.perform(delete("/tags/" + 2)
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}