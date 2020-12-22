package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.users.api.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
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
    private Page<ReviewTO> reviewTOPage;

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
        reviewTO.setBookId(1);

        List<ReviewTO> reviewTOList = new ArrayList<>();
        reviewTOList.add(reviewTO);

        reviewTOPage = new Page<ReviewTO>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super ReviewTO, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<ReviewTO> getContent() {
                return reviewTOList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @NotNull
            @Override
            public Iterator<ReviewTO> iterator() {
                return null;
            }
        };
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
    void getAllByGoogleBook() throws Exception {
        Mockito.when(mockReviewService.getAllByGoogleBook(reviewTO.getIdGoogleBook(), user.getToken(), 0, 1)).thenReturn(reviewTOPage);
        mockMvc.perform(get("/review/google-book/" + reviewTO.getIdGoogleBook())
                .param("page", "0")
                .param("size", "1")
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void getAllByBook() throws Exception {
        Mockito.when(mockReviewService.getAllByBook(reviewTO.getBookId(), user.getToken(), 0, 1)).thenReturn(reviewTOPage);
        mockMvc.perform(get("/review/book/" + reviewTO.getBookId())
                .param("page", "0")
                .param("size", "1")
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void postReview() throws Exception{
        Mockito.when(mockReviewService.save(reviewTO, user.getToken())).thenReturn(reviewTO);
        mockMvc.perform(post("/review")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reviewTO))
                .header(HttpHeaders.AUTHORIZATION,"Basic " + user.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void putReview() throws Exception {
        Mockito.when(mockReviewService.updateReview(reviewTO.getId(), reviewTO, user.getToken())).thenReturn(reviewTO);
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