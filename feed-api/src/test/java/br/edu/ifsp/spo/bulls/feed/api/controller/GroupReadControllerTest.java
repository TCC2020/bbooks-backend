package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupService mockGroupService;

    private GroupTO group;

    @BeforeEach
    void setUp() {
        group = new GroupTO();
        group.setId(UUID.randomUUID());
        group.setUserId(UUID.randomUUID());
        group.setCreationDate(LocalDateTime.now());
        group.setDescription("descricao");
        group.setName("nome");
        group.setPrivacy(Privacy.private_group);
    }

    @Test
    void post() throws Exception {
        Mockito.when(mockGroupService.save(group)).thenReturn(group);

        mockMvc.perform(MockMvcRequestBuilders.post("/group")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isCreated());
    }

    @Test
    void put() throws Exception {
        Mockito.when(mockGroupService.update("Basic 32131989321", group, group.getId())).thenReturn(group);

        mockMvc.perform(MockMvcRequestBuilders.put("/group/" + group.getId())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic 32131989321")
                .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        Mockito.when(mockGroupService.getById(group.getId())).thenReturn(group);

        mockMvc.perform(MockMvcRequestBuilders.get("/group/" + group.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception{
        Mockito.doNothing().when(mockGroupService).delete("token", group.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/group/" + group.getId())
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get() throws Exception{
        Mockito.when(mockGroupService.search("teste", 0, 1)).thenReturn(Page.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/group/")
                .param("name", "teste")
                .param("page", "0")
                .param("size", "1"))
                .andExpect(status().isOk());
    }
}