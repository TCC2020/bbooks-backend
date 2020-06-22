package br.edu.ifsp.spo.bulls.usersApi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
	private UserBeanUtil beanUtil;
    
    @Autowired
	ProfileService service;
    
    @Test
    void testUpdateProfile() throws Exception {
        
    	UserTO userTo = new UserTO();
    	userTo.setUserName("testeUpdateProfile");
    	userTo.setEmail("testeUp@profileController");
    	userTo.setPassword("1234");

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)));
        
        User user = beanUtil.toUser(userTo);
        ProfileTO profile = new ProfileTO("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", user);
		
        mockMvc.perform(put("/profiles")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)));
        
        int id = service.getByUser(user).getId();
       
        profile.setState("RJ");
        profile.setCity("Rio de janeiro");
		
        mockMvc.perform(put("/profiles/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());
        
    }
    
    @Test
    void testUpdateProfileNotFound() throws Exception {
        
    	User user = new User();
    	user.setUserName("testeUpdateProfileNotFound");
    	user.setEmail("testeUp@profileController404");
    	user.setPassword("1234");

    	ProfileTO profile = new ProfileTO("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", user);
		
        mockMvc.perform(put("/profiles/123456" )
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testDeleteProfile() throws Exception {
        
    	UserTO userTo = new UserTO();
    	userTo.setUserName("testeDeleteController");
    	userTo.setEmail("teste@profileDelete");
    	userTo.setPassword("1234");

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)));
        
        User user = beanUtil.toUser(userTo);
        ProfileTO profile = new ProfileTO("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", user);
		
        mockMvc.perform(post("/profiles")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)));
        
        int id = service.getByUser(user).getId();
        
        mockMvc.perform(delete("/profiles/" + id)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
    
    @Test
    void testDeleteProfileNotFound() throws Exception {
   
        mockMvc.perform(delete("/profiles/12345678")
                .contentType("application/json"))
                .andExpect(status().isNotFound());

    }
    
    @Test
    void testeGetByIdProfile() throws Exception {
        
    	UserTO userTo = new UserTO();
    	userTo.setUserName("testeGetByIdProfile");
    	userTo.setEmail("testeGet@profileController");
    	userTo.setPassword("1234");

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userTo)));
        
        User user = beanUtil.toUser(userTo);
        ProfileTO profile = new ProfileTO("nome", "sobrenome", "pais", "sao paulo", "SP", "10/10/1998", user);
		
        mockMvc.perform(put("/profiles")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)));
        
        int id = service.getByUser(user).getId();
       
        mockMvc.perform(get("/profiles/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk());
        
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
