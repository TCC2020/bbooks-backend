package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.users.api.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService mockReviewService;

    private ReviewTO reviewTO;
    private User user;
    private List<ReviewTO> reviewTOList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("email");
        user.setToken("token");
        user.setUserName("username");

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setId(1);

        reviewTO = new ReviewTO();
        reviewTO.setBody("resenha");
        reviewTO.setCreationDate(LocalDateTime.now());
        reviewTO.setId(UUID.randomUUID());
        reviewTO.setTitle("titulo da resenha");
        reviewTO.setIdGoogleBook("idGoogleBook");
        reviewTO.setProfileId(profile.getId());

        reviewTOList = new ArrayList<>();
        reviewTOList.add(reviewTO);
    }

    @Test
    void getOne() throws Exception {
        Mockito.when(mockReviewService.getOneById(reviewTO.getId(), user.getToken())).thenReturn(reviewTO);
        mockMvc.perform(get("/review/" + reviewTO.getId())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void getAllByBook() throws Exception {
        Mockito.when(mockReviewService.getAllByBook(reviewTO.getIdGoogleBook(), user.getToken())).thenReturn(reviewTOList);
        mockMvc.perform(get("/review/book/" + reviewTO.getIdGoogleBook())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void postReview() throws Exception{
        Mockito.when(mockReviewService.postReview(reviewTO, user.getToken())).thenReturn(reviewTO);
        mockMvc.perform(post("/review")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reviewTO))
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void putReview() throws Exception {
        Mockito.when(mockReviewService.putReview(reviewTO.getId(), reviewTO, user.getToken())).thenReturn(reviewTO);
        mockMvc.perform(put("/review/" + reviewTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reviewTO))
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception  {
        Mockito.doNothing().when(mockReviewService).deleteById(reviewTO.getId(), user.getToken());
        mockMvc.perform(delete("/review/" + reviewTO.getId())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }
}