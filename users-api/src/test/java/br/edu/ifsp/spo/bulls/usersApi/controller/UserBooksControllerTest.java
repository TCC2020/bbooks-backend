package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.*;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.Status;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.*;

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

    private BookCaseTO bookCaseTO = new BookCaseTO();
    private UserBooksTO userBooksTO = new UserBooksTO();
    private UserBooks userBooks;
    private Set<UserBooksTO> userBooksList = new HashSet<UserBooksTO>();

    @BeforeEach
    void setUp() {
        userBooksTO.setId(1L);
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBookGoogle("32");

        Set<UserBooksTO> userBooksList = new HashSet<UserBooksTO>();
        userBooksList.add(userBooksTO);
        bookCaseTO.setProfileId(1);
        bookCaseTO.setBooks(userBooksList);

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

        Mockito.when(mockUserBooksService.getByProfileId(1)).thenReturn(bookCaseTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookcases/profile/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void putUserBook() throws Exception {

        UserBooksTO userBooksTO1 = userBooksTO;
        userBooksTO1.setStatus(Status.EMPRESTADO);
        Mockito.when(mockUserBooksService.update(userBooksTO)).thenReturn(userBooksTO1);

        mockMvc.perform(put("/bookcases")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userBooksTO)))
                .andExpect(status().isOk());
    }

    @Test
    void putStatus() throws Exception {

        UserBookUpdateStatusTO updateStatusTO = new UserBookUpdateStatusTO();
        updateStatusTO.setId(1L);
        updateStatusTO.setStatus("lendo");

        Mockito.when(mockUserBooksService.updateStatus(updateStatusTO)).thenReturn(userBooksTO);

        mockMvc.perform(put("/bookcases/status")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userBooksTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        Mockito.doNothing().when(mockUserBooksService).deleteById(userBooksTO.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/bookcases/" + userBooksTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}