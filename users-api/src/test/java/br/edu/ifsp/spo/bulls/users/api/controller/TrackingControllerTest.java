package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.service.TrackingService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrackingService mockTrackingService;

    private UserBooks userBooks = new UserBooks();
    private TrackingTO trackingTO = new TrackingTO();
    private List<TrackingTO> trackingTOList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userBooks.setId(1L);
        trackingTO.setId(UUID.randomUUID());
        trackingTOList.add(trackingTO);
    }

    @Test
    void shouldGetAllTrackingsByUserbook() throws Exception {

        Mockito.when(mockTrackingService.getAllByBook(userBooks.getId())).thenReturn(trackingTOList);

        mockMvc.perform(get("/tracking-group/book/" + userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntGetAllTrackingsByUserbookWhenUserbooksNotFound() throws Exception {
        Mockito.when(mockTrackingService.getAllByBook(userBooks.getId())).thenThrow(new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));

        mockMvc.perform(get("/tracking-group/book/" + userBooks.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetOneTrackingById() throws Exception {
        Mockito.when(mockTrackingService.findById(trackingTO.getId())).thenReturn(trackingTO);

        mockMvc.perform(get("/tracking-group/" + trackingTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldntGetOneTrackingByIdWhenTrackingNotFound() throws Exception {
        Mockito.when(mockTrackingService.findById(trackingTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));

        mockMvc.perform(get("/tracking-group/" + trackingTO.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSaveTracking()  throws Exception {
        Mockito.when(mockTrackingService.save(trackingTO)).thenReturn(trackingTO);
        mockMvc.perform(post("/tracking-group")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntSaveTrackingWhenOtherIsOpen()  throws Exception {
        Mockito.when(mockTrackingService.save(trackingTO)).thenThrow(new ResourceConflictException(CodeException.TA001.getText(), CodeException.TA001));
        mockMvc.perform(post("/tracking-group" )
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldntSaveTrackingWhenUserbooksNotFound()  throws Exception {
        Mockito.when(mockTrackingService.save(trackingTO)).thenThrow(new ResourceNotFoundException(CodeException.TA001.getText(), CodeException.TA001));
        mockMvc.perform(post("/tracking-group")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTracking() throws Exception {
        Mockito.doNothing().when(mockTrackingService).deleteById(trackingTO.getId());
        mockMvc.perform(delete("/tracking-group/" + trackingTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntDeleteTrackingWhenTrackingNotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002)).when(mockTrackingService).deleteById(trackingTO.getId());
        mockMvc.perform(delete("/tracking-group/" + trackingTO.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPpdateTracking() throws Exception {
        Mockito.when(mockTrackingService.update(trackingTO.getId(), trackingTO)).thenReturn(trackingTO);
        mockMvc.perform(put("/tracking-group/" + trackingTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldntUpdateTrackingWhenTrackingNotFound() throws Exception {
        Mockito.when(mockTrackingService.update(trackingTO.getId(), trackingTO)).thenThrow(new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
        mockMvc.perform(put("/tracking-group/" + trackingTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingTO)))
                .andExpect(status().isNotFound());
    }
}