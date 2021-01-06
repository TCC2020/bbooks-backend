package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.service.ReadingTrackingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReadingTrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReadingTrackingService mockReadingTrackingService;

    private ReadingTrackingTO readingTrackingTO;

    @BeforeEach
    void setUp(){
        readingTrackingTO = new ReadingTrackingTO();
        readingTrackingTO.setId(UUID.randomUUID());
    }


    @Test
    void testDeleteTracking() throws Exception {
        Mockito.doNothing().when(mockReadingTrackingService).delete(readingTrackingTO.getId());

        mockMvc.perform(delete("/tracking/" + readingTrackingTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)).when(mockReadingTrackingService).delete(readingTrackingTO.getId());
        mockMvc.perform(delete("/tracking/" + readingTrackingTO.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOneTracking() throws Exception {
        Mockito.when(mockReadingTrackingService.get(readingTrackingTO.getId())).thenReturn(readingTrackingTO);

        mockMvc.perform(get("/tracking/" + readingTrackingTO.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOneNotFound() throws Exception {
        Mockito.when(mockReadingTrackingService.get(readingTrackingTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));
        mockMvc.perform(get("/tracking/" + readingTrackingTO.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostReadingTracking() throws Exception {
        Mockito.when(mockReadingTrackingService.save(readingTrackingTO)).thenReturn(readingTrackingTO);
        mockMvc.perform(post("/tracking")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(readingTrackingTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testPutReadingOk() throws Exception {
        Mockito.when(mockReadingTrackingService.update(readingTrackingTO, readingTrackingTO.getId())).thenReturn(readingTrackingTO);
        mockMvc.perform(put("/tracking/" + readingTrackingTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(readingTrackingTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testPutReadingNotFound() throws Exception {
        Mockito.when(mockReadingTrackingService.update(readingTrackingTO,readingTrackingTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));

        mockMvc.perform(put("/tracking/" + readingTrackingTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(readingTrackingTO)))
                .andExpect(status().isNotFound());
    }
}
