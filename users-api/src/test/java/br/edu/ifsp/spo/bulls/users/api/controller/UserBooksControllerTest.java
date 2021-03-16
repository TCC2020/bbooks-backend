package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksDataStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.users.api.service.UserBooksService;
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
import java.util.HashSet;
import java.util.Set;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private UserBooksDataStatusTO userBooksDataStatusTO = new UserBooksDataStatusTO();

    @BeforeEach
    void setUp() {
        userBooksTO.setId(1L);
        userBooksTO.setProfileId(1);
        userBooksTO.setIdBookGoogle("32");

        Set<UserBooksTO> userBooksList = new HashSet<>();
        userBooksList.add(userBooksTO);
        bookCaseTO.setProfileId(1);
        bookCaseTO.setBooks(userBooksList);

        userBooksDataStatusTO.setBookId(1);
        userBooksDataStatusTO.setLido(100L);

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

        Mockito.when(mockUserBooksService.getByProfileId(1, false)).thenReturn(bookCaseTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookcases/profile/1")
                .param("timeLine", "false")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getInfoOfBook() throws Exception {

        Mockito.when(mockUserBooksService.getStatusData("", 1)).thenReturn(userBooksDataStatusTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookcases/status-data")
                .param("googleBook", "")
                .param("bookId", "1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void putUserBook() throws Exception {

        UserBooksTO userBooksTO1 = userBooksTO;
        userBooksTO1.setStatus(Status.EMPRESTADO);
        Mockito.when(mockUserBooksService.update(userBooksTO, userBooksTO.getId())).thenReturn(userBooksTO1);

        mockMvc.perform(MockMvcRequestBuilders.put("/bookcases/" + userBooksTO.getId())
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

        mockMvc.perform(MockMvcRequestBuilders.put("/bookcases/status")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userBooksTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        Mockito.doNothing().when(mockUserBooksService).delete(userBooksTO.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/bookcases/" + userBooksTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}