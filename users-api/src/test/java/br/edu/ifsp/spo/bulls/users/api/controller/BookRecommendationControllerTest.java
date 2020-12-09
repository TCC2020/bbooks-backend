package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.bean.BookRecommendationBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.users.api.service.BookRecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookRecommendationControllerTest {

    @MockBean
    BookRecommendationService mockRecommendationService;

    @Autowired
    BookRecommendationBeanUtil beanUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Profile profileSubmitter;
    private Profile profileReceived;
    private BookRecommendationTO bookRecommendationTO;
    private List<BookRecommendationTO> bookRecommendationTOList;

    @BeforeEach
    void setUp(){

        profileReceived = new Profile();
        profileReceived.setId(1);

        profileSubmitter = new Profile();
        profileSubmitter.setId(2);

        bookRecommendationTO = new BookRecommendationTO();
        bookRecommendationTO.setId(UUID.randomUUID());
        bookRecommendationTO.setProfileReceived(profileReceived.getId());
        bookRecommendationTO.setProfileSubmitter(profileSubmitter.getId());
        bookRecommendationTO.setIdBookGoogle("idbook");

        bookRecommendationTOList = new ArrayList<>();
        bookRecommendationTOList.add(bookRecommendationTO);
    }

    @Test
    void shouldSaveRecommendation() throws Exception {
        Mockito.when(mockRecommendationService.save(bookRecommendationTO)).thenReturn(bookRecommendationTO);
        mockMvc.perform(post("/book-recommendation")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookRecommendationTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteRecommendation() throws Exception {
        Mockito.doNothing().when(mockRecommendationService).delete(bookRecommendationTO.getId());
        mockMvc.perform(delete("/book-recommendation/" + bookRecommendationTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateRecommendation() throws Exception {
        Mockito.when(mockRecommendationService.update(bookRecommendationTO, bookRecommendationTO.getId())).thenReturn(bookRecommendationTO);
        mockMvc.perform(put("/book-recommendation/" + bookRecommendationTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookRecommendationTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRecommentionsSent() throws Exception {
        Mockito.when(mockRecommendationService.getRecommentionsSent(profileSubmitter.getId())).thenReturn(bookRecommendationTOList);
        mockMvc.perform(get("/book-recommendation/" + profileSubmitter.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRecommentionsReceived() throws Exception {
        Mockito.when(mockRecommendationService.getRecommentionsReceived(profileReceived.getId())).thenReturn(bookRecommendationTOList);
        mockMvc.perform(get("/book-recommendation/received/" + profileReceived.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
