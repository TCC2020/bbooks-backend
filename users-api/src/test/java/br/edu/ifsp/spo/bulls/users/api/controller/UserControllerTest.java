package br.edu.ifsp.spo.bulls.users.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.dto.CadastroUserTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.service.UserService;
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
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
	private UserService mockUserService;

    @Autowired
	private UserBeanUtil userBeanUtil;

    private CadastroUserTO cadastroUserTO;
    private UserTO userTO;
    private User user;
    private HashSet<UserTO> userTOList;

    @BeforeEach
    public void setUp(){
        cadastroUserTO = new CadastroUserTO();
        cadastroUserTO.setId(UUID.randomUUID());
        cadastroUserTO.setUserName("testeID");
        cadastroUserTO.setEmail("teste@testeID");
        cadastroUserTO.setPassword("1234567");
        cadastroUserTO.setName("nome");
        cadastroUserTO.setLastName("sobrenome");
        cadastroUserTO.setToken("token");

        user = userBeanUtil.toUser(cadastroUserTO);

        userTO = userBeanUtil.toUserTO(user);

        userTOList = new HashSet<>();
        userTOList.add(userTO);
    }
    @Test
    void shouldCreateUser() throws Exception {
        Mockito.when(mockUserService.save(cadastroUserTO)).thenReturn(userTO);
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(cadastroUserTO)))
                .andExpect(status().isOk());

    }
    
    @Test
    void shouldGetAllUsers() throws Exception {
        Mockito.when(mockUserService.getAll()).thenReturn(userTOList);

        mockMvc.perform(get("/users")
        		.contentType("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldGetOneUserById() throws Exception {
        Mockito.when(mockUserService.getById(cadastroUserTO.getId())).thenReturn(userTO);

        mockMvc.perform(get("/users/" + cadastroUserTO.getId())
        		.contentType("application/json"))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldUpdateUser() throws  Exception {
        Mockito.when(mockUserService.update(userTO)).thenReturn(userTO);

		mockMvc.perform(put("/users/" + userTO.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(userTO)))
				.andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Mockito.doNothing().when(mockUserService).delete(userTO.getId());

        mockMvc.perform(delete("/users/" + userTO.getId())
        		.contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
	void shouldgetInfoByToken() throws Exception {
        Mockito.when(mockUserService.getByToken(userTO.getToken())).thenReturn(user);

		mockMvc.perform(get("/users/info")
				.contentType("application/json")
				.header("AUTHORIZATION", userTO.getToken() ))
				.andExpect(status().isOk());
	}

	@Test
    void shouldgetInfoByEmail() throws Exception {
        Mockito.when(mockUserService.getByEmail(userTO.getEmail())).thenReturn(userTO);

        mockMvc.perform(get("/users/email/" + userTO.getEmail())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetUserForGoogle() throws Exception {
        Mockito.when(mockUserService.getForGoogle(userTO.getEmail())).thenReturn(userTO);

        mockMvc.perform(get("/users/google/" + userTO.getEmail())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetUserByUserName() throws Exception {
        Mockito.when(mockUserService.getByUserName(userTO.getUserName(), userTO.getToken())).thenReturn(userTO);

        mockMvc.perform(get("/users/username/" + userTO.getUserName())
                .contentType("application/json")
                .header("AUTHORIZATION", userTO.getToken()))
                .andExpect(status().isOk());
    }
}
