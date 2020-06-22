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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreateUser() throws Exception {
        
    	UserTO user = new UserTO();
    	user.setUserName("teste");
    	user.setEmail("teste@teste");
    	user.setPassword("1234567");
    	user.setName("nome");
    	user.setLastName("sobrenome");
    	
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

    }

    @Test
    void testCreateUserUserNameIgual() throws Exception {
        
    	UserTO user = new UserTO();
    	user.setUserName("teste1");
    	user.setEmail("teste@teste");
    	user.setPassword("1234");

    	
    	UserTO user1 = new UserTO();
    	user.setUserName("teste1");
    	user.setEmail("teste@teste123");
    	user.setPassword("1234");
    	
    	// Criando um usuário 
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)));

        // testando criar um usuário com Username repetido 
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testSaveEmailVazio() throws JsonProcessingException, Exception {
        
    	// Criando o usuário
    	UserTO user = new UserTO();
    	user.setUserName("testeSEmailVazio");
    	user.setEmail(null);
    	user.setPassword("1234");
    	
        mockMvc.perform(post("/users")
        		.contentType("application/json")
        		.content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testSavePasswordVazio() throws JsonProcessingException, Exception {
        
    	// Criando o usuário
    	UserTO user = new UserTO();
    	user.setUserName("testeSenhaVazio");
    	user.setEmail("teste@testeSUsernameVazio");
    	user.setPassword(null);

        mockMvc.perform(post("/users")
        		.contentType("application/json")
        		.content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());

    }
    
    @Test
    void testGetAll() throws Exception {
 
        // Recuperando o usuário
        mockMvc.perform(get("/users")
        		.contentType("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        
    	UserTO user = new UserTO();
    	user.setUserName("testeID");
    	user.setEmail("teste@testeID");
    	user.setPassword("1234567");
    	user.setName("nome");
    	user.setLastName("sobrenome");
    	// Criando o usuário
    	
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
        
        // Recuperando o usuário
        mockMvc.perform(get("/users/testeID")
        		.contentType("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetByIdUserNotFound() throws Exception {
        
        // Recuperando o usuário
        mockMvc.perform(get("/users/testeID123")
        		.contentType("application/json"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testUpdate() throws JsonProcessingException, Exception {
        
    	// Criando o usuário
    	UserTO user = new UserTO();
    	user.setUserName("testeUP");
    	user.setEmail("teste@testeUP");
    	user.setPassword("1234567");
    	user.setName("nome");
    	user.setLastName("sobrenome");
    	
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
        
        // Alterando o usuário
        
        user.setEmail("testeUP@teste");

        mockMvc.perform(put("/users/testeUP")
        		.contentType("application/json")
        		.content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }
    
    @Test
    void testUpdateUserNotFound() throws JsonProcessingException, Exception {
        
    	// Criando o usuário
    	UserTO user = new UserTO();
    	user.setUserName("testeUPNotFound");
    	user.setEmail("teste@testeUPFound");
    	user.setPassword("1234567");

        mockMvc.perform(put("/users/testeUPNotFound")
        		.contentType("application/json")
        		.content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws JsonProcessingException, Exception {
        
    	// Criando o usuário
    	UserTO user = new UserTO();
    	user.setUserName("testeDelete");
    	user.setEmail("teste@testeDEL");
    	user.setPassword("1234567");
    	user.setName("nome");
    	user.setLastName("sobrenome");
    	
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)));
        
        // Apagando o usuário
        
        mockMvc.perform(delete("/users/testeDelete")
        		.contentType("application/json"))
                .andExpect(status().isOk());

    }
    
    @Test
    void testDeleteUserNotFound() throws JsonProcessingException, Exception {

        // Apagando o usuário
        
        mockMvc.perform(delete("/users/testeDelete")
        		.contentType("application/json"))
                .andExpect(status().isNotFound());

    }
    
}
