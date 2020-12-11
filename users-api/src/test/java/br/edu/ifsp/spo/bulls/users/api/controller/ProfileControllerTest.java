package br.edu.ifsp.spo.bulls.users.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.edu.ifsp.spo.bulls.users.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ProfileService service;
    
    @Test
    void testUpdateProfile() throws Exception {
//        CadastroUserTO userTo = new CadastroUserTO("testeUpdateProfile", "testeUp@profileController", "senhate", "nome", "sobrenome");
//
//    	String userName = userService.save(userTo).getUserName();
//
//        ProfileTO profile = service.getByUser(userName);
//
//        profile.setState("RJ");
//        profile.setCity("Rio de janeiro");
//
//        mockMvc.perform(put("/profiles/" + profile.getId())
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(profile)))
//                .andExpect(status().isOk());
        
    }
    
    @Test
    void testUpdateProfileNotFound() throws Exception {

    	ProfileTO profile = new ProfileTO("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998");
		
        mockMvc.perform(put("/profiles/123456" )
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testDeleteProfile() throws Exception {

//        CadastroUserTO userTo = new CadastroUserTO("testeDeleteController", "teste@profileDelete", "senhate", "nome", "sobrenome");
//
//    	String userName = userService.save(userTo).getUserName();
//
//        int id = service.getByUser(userName).getId();
//
//        mockMvc.perform(delete("/profiles/" + id)
//                .contentType("application/json"))
//                .andExpect(status().isOk());

    }
    
    @Test
    void testDeleteProfileNotFound() throws Exception {
   
        mockMvc.perform(delete("/profiles/12345678")
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }
    
    @Test
    void testeGetByIdProfile() throws Exception {

//        CadastroUserTO userTo = new CadastroUserTO("testeUpdateOk", "testeS@updateOk", "senhate", "nome", "sobrenome");
//
//        String userName = userService.save(userTo).getUserName();
//
//        int id = service.getByUser(userName).getId();
//
//        mockMvc.perform(get("/profiles/" + id)
//                .contentType("application/json"))
//                .andExpect(status().isOk());
    }

    @Test
    void testeGetByUser() throws Exception {
//
//        CadastroUserTO userTo = new CadastroUserTO("testGetByUser", "testeS@up", "senhate", "nome", "sobrenome");
//
//        String userName = userService.save(userTo).getUserName();
//
//        mockMvc.perform(get("/profiles/user/" + userName)
//                .contentType("application/json"))
//                .andExpect(status().isOk());
    }

    @Test
    void testGetProfileNotFound() throws Exception {
   
        mockMvc.perform(get("/profiles/12345678")
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testeGetAllProfiles() throws Exception {
     
        mockMvc.perform(get("/profiles")
                .contentType("application/json"))
                .andExpect(status().isOk());
        
    }
}
