package br.edu.ifsp.spo.bulls.usersApi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.usersApi.service.UserService;
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
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
	private UserService service;

    @Autowired
	private UserBeanUtil userBeanUtil;
    
    @Test
    void testCreateUser() throws Exception {
        
//    	CadastroUserTO user = new CadastroUserTO();
//    	user.setUserName("teste");
//    	user.setEmail("teste@teste");
//    	user.setPassword("1234567");
//    	user.setName("nome");
//    	user.setLastName("sobrenome");
//
//        mockMvc.perform(post("/users")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk());

    }

    @Test
    void testCreateUserUserNameIgual() throws Exception {

		CadastroUserTO user = new CadastroUserTO();
    	user.setUserName("teste1");
    	user.setEmail("teste@teste");
    	user.setPassword("1234");

    	
    	UserTO user1 = new UserTO();
    	user.setUserName("teste1");
    	user.setEmail("teste@teste123");
    	user.setPassword("1234");
    	
    	// Criando um usu�rio 
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)));

        // testando criar um usu�rio com Username repetido 
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testSaveEmailVazio() throws  Exception {
        
    	// Criando o usuario
		CadastroUserTO user = new CadastroUserTO();
    	user.setUserName("testeSEmailVazio");
    	user.setEmail(null);
    	user.setPassword("1234");
    	
        mockMvc.perform(post("/users")
        		.contentType("application/json")
        		.content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testSavePasswordVazio() throws Exception {
        
    	// Criando o usu�rio
		CadastroUserTO user = new CadastroUserTO();
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
 
        // Recuperando o usu�rio
        mockMvc.perform(get("/users")
        		.contentType("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {

//		CadastroUserTO user = new CadastroUserTO();
//    	user.setUserName("testeID");
//    	user.setEmail("teste@testeID");
//    	user.setPassword("1234567");
//    	user.setName("nome");
//    	user.setLastName("sobrenome");
//    	// Criando o usuario
//
//		UserTO res = service.save(user);
//
//        // Recuperando o usuario
//        mockMvc.perform(get("/users/" + res.getId())
//        		.contentType("application/json"))
//                .andExpect(status().isOk());
    }
    
    @Test
    void testGetByIdUserNotFound() throws Exception {
        
        // Recuperando o usuario
        mockMvc.perform(get("/users/" + UUID.randomUUID())
        		.contentType("application/json"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testUpdate() throws  Exception {

		// Criando o usuario
//		CadastroUserTO user = new CadastroUserTO();
//		user.setUserName("testeU");
//		user.setEmail("teste@test");
//		user.setPassword("1234567");
//		user.setName("nome");
//		user.setLastName("sobrenome");
//
//		UserTO res = service.save(user);
//
//		// Alterando o usuario
//		res.setEmail("testeUP@teste");
//		//alterar.setPassword(user.getPassword());
//
//		mockMvc.perform(put("/users/" + res.getId())
//				.contentType("application/json")
//				.content(objectMapper.writeValueAsString(res)))
//				.andExpect(status().isOk());
    }
    
    @Test
    void testUpdateUserNotFound() throws  Exception {
        
    	// Criando o usuario
		UserTO user = new UserTO();
    	user.setUserName("testeUPNotFound");
    	user.setEmail("teste@testeUPFound");

        mockMvc.perform(put("/users/" + UUID.randomUUID())
        		.contentType("application/json")
        		.content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {
        
    	// Criando o usuario
//		CadastroUserTO user = new CadastroUserTO();
//    	user.setUserName("testeDelete");
//    	user.setEmail("teste@testeDEL");
//    	user.setPassword("1234567");
//    	user.setName("nome");
//    	user.setLastName("sobrenome");
//
//    	UserTO res = service.save(user);
//
//        // Apagando o usuario
//        mockMvc.perform(delete("/users/" + res.getId())
//        		.contentType("application/json"))
//                .andExpect(status().isOk());

    }
    
    @Test
    void testDeleteUserNotFound() throws  Exception {

        // Apagando o usuario
        mockMvc.perform(delete("/users/" + UUID.randomUUID())
        		.contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
	void testGetInfoByToken() throws Exception {
//		// Criando o usuario
//		CadastroUserTO user = new CadastroUserTO();
//		user.setUserName("testeUP");
//		user.setEmail("teste@testeUP");
//		user.setPassword("1234567");
//		user.setName("nome");
//		user.setLastName("sobrenome");
//
//		UserTO res = service.save(user);
//
//		// Criando o token
//		LoginTO loginTo = new LoginTO(user.getUserName(),user.getEmail(), user.getPassword() );
//
//		mockMvc.perform(post("/auth/confirm")
//				.contentType("application/json")
//				.content(objectMapper.writeValueAsString(loginTo)));
//
//		String token  = service.getById(res.getId()).getToken();
//
//		// Requisição
//		mockMvc.perform(get("/users/info")
//				.contentType("application/json")
//				.header("AUTHORIZATION", token ))
//				.andExpect(status().isOk());
	}
}
