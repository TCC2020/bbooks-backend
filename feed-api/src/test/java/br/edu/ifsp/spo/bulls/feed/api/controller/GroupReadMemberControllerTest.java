package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.service.GroupMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupReadMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupMemberService mockGroupMemberService;

    private GroupMembers groupMembers;
    private GroupMemberTO groupMemberTO;
    private GroupRead groupRead;
    private List<GroupRead> groupReadList;
    private List<GroupMembers> groupMembersList;


    @BeforeEach
    void setUp() {
        groupRead = new GroupRead();
        groupRead.setId(UUID.randomUUID());

        GroupMemberId id = new GroupMemberId();
        id.setGroupRead(groupRead);
        id.setUser(UUID.randomUUID());

        groupMembers = new GroupMembers();
        groupMembers.setRole(Role.admin);
        groupMembers.setDate(LocalDateTime.now());
        groupMembers.setId(id);

        groupMembersList = new ArrayList<>();
        groupMembersList.add(groupMembers);

        groupReadList = new ArrayList<>();
        groupReadList.add(groupRead);

        groupMemberTO = new GroupMemberTO();
        groupMemberTO.setUserId(groupMembers.getId().getUser());
        groupMemberTO.setGroupId(groupMembers.getId().getGroupRead().getId());
        groupMemberTO.setDate(groupMembers.getDate());
        groupMemberTO.setRole(groupMembers.getRole());
    }

    @Test
    void enterGroup() throws Exception {
        Mockito.doNothing().when(mockGroupMemberService).putMember(groupMemberTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/group/member")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(groupMemberTO)))
                .andExpect(status().isOk());
    }

    @Test
    void exitGroup() throws Exception {
        Mockito.doNothing().when(mockGroupMemberService).exitMember(groupMemberTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/group/member")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(groupMemberTO)))
                .andExpect(status().isOk());
    }

    @Test
    void getGroupMembers() throws Exception {
        Mockito.when(mockGroupMemberService.getGroupMembers(groupRead.getId())).thenReturn(groupMembersList);

        mockMvc.perform(MockMvcRequestBuilders.get("/group/member/" + groupRead.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserGroups() throws Exception {
        Mockito.when(mockGroupMemberService.getGroupByUser(groupMemberTO.getUserId())).thenReturn(groupReadList);

        mockMvc.perform(MockMvcRequestBuilders.get("/group/member/user/" + groupMemberTO.getUserId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}