package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.service.TrackingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrackingService mockTrackingService;

    @Test
    void should_get_all_trackings_by_userbook() {
    }

    @Test
    void shouldnt_get_all_trackings_by_userbook_when_userbooks_not_found() {
    }

    @Test
    void should_get_one_tracking_by_id() {
    }

    @Test
    void shouldnt_get_one_tracking_by_id_when_tracking_not_found() {
    }

    @Test
    void should_save_tracking() {
    }

    @Test
    void shouldnt_save_tracking_when_other_is_open() {
    }

    @Test
    void should_delete_tracking() {
    }

    @Test
    void shouldnt_delete_tracking_when_tracking_not_found() {
    }

    @Test
    void should_update_tracking() {
    }

    @Test
    void shouldnt_update_tracking_when_tracking_not_found() {
    }
}