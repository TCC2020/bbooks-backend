package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import br.edu.ifsp.spo.bulls.usersApi.service.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    AuthorService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save() throws Exception {

        AuthorTO author = new AuthorTO("autor1");

        mockMvc.perform(post("/authors")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {

        mockMvc.perform( get("/authors")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void getOne() throws Exception {

        int id = service.save(new AuthorTO("autor2ad")).getId();

        mockMvc.perform( get("/authors/" + id)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getOneFail() throws Exception {

        mockMvc.perform( get("/authors/" + new Random().nextInt())
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }

    @Test
    void update() throws Exception {
        AuthorTO author = service.save(new AuthorTO("autor2342352"));

        author.setName("MUDEi");
        mockMvc.perform( put("/authors/" + author.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk());
    }

    @Test
    void updateFail() throws Exception {
        AuthorTO author = new AuthorTO("autor2342352");

        mockMvc.perform( put("/authors/" + new Random().nextInt())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOne() throws Exception {

        int id = service.save(new AuthorTO("autor35325")).getId();

        mockMvc.perform( delete("/authors/" + id)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteFail() throws Exception {

        mockMvc.perform( delete("/authors/" + new Random().nextInt())
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }

    @Test
    void getByName() throws Exception {

        String name = service.save(new AuthorTO("autor12656")).getName();

        mockMvc.perform( get("/authors/name/" + name)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
}