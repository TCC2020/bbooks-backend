package br.edu.ifsp.spo.bulls.users.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfileService mockProfileService;

    private ProfileTO profileTO;
    private HashSet<ProfileTO> profileTOList;

    @BeforeEach
    public void setUp(){
        profileTO = new ProfileTO("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
        profileTO.setId(1);

        profileTOList = new HashSet<>();
        profileTOList.add(profileTO);
    }

    @Test
    void testUpdateProfile() throws Exception {
        Mockito.when(mockProfileService.update(profileTO)).thenReturn(profileTO);
        mockMvc.perform(put("/profiles/" + profileTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profileTO)))
                .andExpect(status().isOk());
    }
    
    @Test
    void testDeleteProfile() throws Exception {
        Mockito.doNothing().when(mockProfileService).delete(profileTO.getId());
        mockMvc.perform(delete("/profiles/" + profileTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testeGetByIdProfile() throws Exception {
        Mockito.when(mockProfileService.getById(profileTO.getId())).thenReturn(profileTO);
        mockMvc.perform(get("/profiles/" + profileTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testeGetByUser() throws Exception {
        Mockito.when(mockProfileService.getByUser(profileTO.getUsername())).thenReturn(profileTO);
        mockMvc.perform(get("/profiles/user/" + profileTO.getUsername())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testeGetAllProfiles() throws Exception {
        Mockito.when(mockProfileService.getAll()).thenReturn(profileTOList);
        mockMvc.perform(get("/profiles")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
