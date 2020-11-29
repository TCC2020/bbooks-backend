package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.service.UserBooksService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserBooksService mockUserBooksService;

    private BookCaseTO bookCaseTO;

    private UserBooksTO userBooksTO;

    @BeforeEach
    void setUp() {
        userBooksTO.setId(1L);
        List<Author> authors = new ArrayList<Author>();
        authors.add( new Author("nome"));
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBook("32");
        userBooksTO.setTags( new ArrayList<Tag>());

        List<UserBooksTO> userBooksList = new ArrayList<>();
        userBooksList.add(userBooksTO);
        bookCaseTO.setProfileId(1);
        bookCaseTO.setBooks(new Book());

    }

    @Test
    void post() throws Exception {
        Mockito.when(mockUserBooksService.save(userBooksTO)).thenReturn(userBooksTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/bookcases")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userBooksTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllByProfile() throws Exception {

        Mockito.when(mockUserBooksService.getByProfileId(1)).thenReturn((BookCaseTO) userBooksTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookcases/profile/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void putUserBook() {
    }

    @Test
    void putStatus() {
    }

    @Test
    void deleteById() {
    }
}