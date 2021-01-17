package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService mockPostService;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(UUID.randomUUID());
        post.setProfileId(1);
        post.setCreationDate(LocalDateTime.now());
        post.setDescription("post");
    }

    @Test
    void post() throws Exception {
        Mockito.when(mockPostService.create(post)).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.post("/post")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());
    }

    @Test
    void put() throws Exception {
        Mockito.when(mockPostService.update(post, post.getId())).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.put("/post/" + post.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());
    }

    @Test
    void get()  throws Exception{
        Mockito.when(mockPostService.get(post.getId())).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/post/" + post.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getByProfile() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        Mockito.when(mockPostService.getByProfile(post.getProfileId())).thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/post/profile/" + post.getProfileId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        Mockito.doNothing().when(mockPostService).delete(post.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/post/" + post.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}