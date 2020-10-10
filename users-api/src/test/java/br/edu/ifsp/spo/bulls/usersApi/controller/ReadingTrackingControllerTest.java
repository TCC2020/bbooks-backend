package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;
import br.edu.ifsp.spo.bulls.usersApi.service.ReadingTrackingService;
import br.edu.ifsp.spo.bulls.usersApi.service.UserBooksService;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReadingTrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserBooksService userBooksService;

    @Autowired
    private ReadingTrackingService readingTrackingService;

    @Test
    void testDeleteTracking() throws Exception {
        int idProfile = this.umProfile("testeDeleteAcOk", "teste@deleteAcOk");
        long userBookId = this.umUserBook(idProfile, UserBooks.Status.LENDO, "1234teste", 30);

        ReadingTrackingTO readingTrackingTO1 = umReadingTracking(userBookId, 20);

        mockMvc.perform(delete("/tracking/" + readingTrackingTO1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteNotFound() throws JsonProcessingException, Exception {
        mockMvc.perform(delete("/tracking/" + UUID.randomUUID())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTracking() throws Exception {
        int idProfile = this.umProfile("testeGetOneAcOk", "teste@GetOneAcOk");
        long userBookId = this.umUserBook(idProfile, UserBooks.Status.LENDO, "1234teste", 30);

        ReadingTrackingTO readingTrackingTO1 = umReadingTracking(userBookId, 20);

        mockMvc.perform(get("/tracking/" + readingTrackingTO1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOneNotFound() throws Exception {
        mockMvc.perform(get("/tracking/" + UUID.randomUUID())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllByBookNotFound() throws Exception {
        mockMvc.perform(get("/tracking/book/" + new Random().nextLong())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllByBook() throws Exception {
        int idProfile = this.umProfile("TesteGetAllByBookOk", "teste@TesteGetAllByBookOk");
        long userBookId = this.umUserBook(idProfile, UserBooks.Status.LENDO, "1234teste", 30);

        ReadingTrackingTO readingTrackingTO1 = umReadingTracking(userBookId, 20);

        mockMvc.perform(get("/tracking/book/" + userBookId)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }




    private ReadingTrackingTO umReadingTracking(long userBookId, int numberPage) {
        ReadingTrackingTO readingTrackingTO = new ReadingTrackingTO();
        readingTrackingTO.setUserBookId(userBookId);
        readingTrackingTO.setNumPag(numberPage);
        return readingTrackingService.save(readingTrackingTO);
    }

    private int umProfile(String userName, String email) throws Exception {
        CadastroUserTO cadastroUserTO = new CadastroUserTO(userName, email, "senhate", "nome", "sobrenome");
        UserTO userTO = userService.save(cadastroUserTO);

        return profileService.getByUser(userTO.getUserName()).getId();
    }

    private Long umUserBook(int profileID, UserBooks.Status status, String idBook, int page){
        UserBooksTO userBooksTO = new UserBooksTO();
        userBooksTO.setProfileId(profileID);
        userBooksTO.setStatus(status);
        userBooksTO.setIdBook(idBook);
        userBooksTO.setPage(page);
        userBooksTO.setTags(new ArrayList<Tag>());
        return userBooksService.save(userBooksTO).getId();
    }
}
