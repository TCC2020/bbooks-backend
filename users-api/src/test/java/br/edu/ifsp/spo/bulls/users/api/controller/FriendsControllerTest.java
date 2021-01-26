package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.common.api.domain.Friendship;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendRequestTO;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendTO;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendshipTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.users.api.dto.AcceptTO;
import br.edu.ifsp.spo.bulls.users.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.users.api.service.FriendshipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FriendshipService mockFriendshipService;

    private FriendshipTO friendshipTO;
    private List<FriendRequestTO> friendshipTOList;

    private UserTO userTO;
    private FriendTO friendTO;
    private Friendship friendship;
    private AcceptTO acceptTO;

    @BeforeEach
    void setUp() {
        ProfileTO profileTO = new ProfileTO();
        profileTO.setId(2);

        userTO = new UserTO();
        userTO.setId(UUID.randomUUID());
        userTO.setEmail("email");
        userTO.setProfile(profileTO);
        userTO.setToken("token");
        userTO.setUserName("username");

        HashSet<UserTO> userTOS = new HashSet<>();
        userTOS.add(userTO);

        friendshipTO = new FriendshipTO();
        friendshipTO.setFriends(userTOS);
        friendshipTO.setProfileId(profileTO.getId());

        friendTO = new FriendTO();
        friendTO.setId(1);

        friendship = new Friendship();
        friendship.setId(1L);
        friendship.setAddDate(LocalDateTime.now());
        friendship.setProfile1(1);
        friendship.setProfile2(profileTO.getId());

        acceptTO = new AcceptTO();
        acceptTO.setId(friendship.getId());

        friendshipTOList = new ArrayList<>();
        friendshipTOList.add(new FriendRequestTO());

    }

    @Test
    void getFriends() throws Exception {
        Mockito.when(mockFriendshipService.getFriends(userTO.getToken())).thenReturn(friendshipTO);

        mockMvc.perform(get("/friends")
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFriend() throws Exception {
        Mockito.when(mockFriendshipService.deleteFriend(userTO.getToken(), friendshipTO.getProfileId())).thenReturn(HttpStatus.OK);

        mockMvc.perform(delete("/friends/" + friendshipTO.getProfileId())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void getByUsername() throws Exception {
        Mockito.when(mockFriendshipService.getFriendsByUsername(userTO.getUserName(),userTO.getToken())).thenReturn(friendshipTO);

        mockMvc.perform(get("/friends/" + userTO.getUserName())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void add() throws Exception {
        Mockito.when(mockFriendshipService.add(friendTO, userTO.getToken())).thenReturn(HttpStatus.CREATED);
        mockMvc.perform(post("/friends/requests")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(friendTO))
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRequests() throws Exception {
        Mockito.when(mockFriendshipService.getRequests(userTO.getToken())).thenReturn(friendshipTOList);
        mockMvc.perform(get("/friends/requests")
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void getRequestsByUsername() throws Exception {
        Mockito.when(mockFriendshipService.getRequestByRequest(userTO.getToken(), userTO.getUserName())).thenReturn(friendship);

        mockMvc.perform(get("/friends/requests/" + userTO.getUserName())
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void responseRequest() throws Exception{
        Mockito.when(mockFriendshipService.accept(userTO.getToken(), acceptTO.getId())).thenReturn(HttpStatus.OK);
        mockMvc.perform(put("/friends/requests/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(acceptTO))
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }

    @Test
    void reject() throws Exception {
        Mockito.when(mockFriendshipService.reject(userTO.getToken(), acceptTO.getId())).thenReturn(HttpStatus.OK);
        mockMvc.perform(delete("/friends/requests/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(acceptTO))
                .header(HttpHeaders.AUTHORIZATION,"Basic " + userTO.getToken() ))
                .andExpect(status().isOk());
    }
}