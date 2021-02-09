package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class, classes = {GroupMemberService.class})
public class GroupReadMemberServiceTest {

    @Autowired
    private GroupMemberService service;

    @MockBean
    private GroupMemberRepository mockGroupMemberRepository;

    @MockBean
    private UserCommonFeign feign;

    private GroupMembers groupMembers;
    private List<GroupMembers> groupMembersList;
    private GroupRead groupRead;
    private List<GroupRead> groupReadList;

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
    }

    @Test
    void putMember() {
        Mockito.when(mockGroupMemberRepository.save(groupMembers)).thenReturn(groupMembers);

        service.putMember(groupMembers);

        Mockito.verify(mockGroupMemberRepository).save(groupMembers);
    }

    @Test
    void exitMember() {
        Mockito.doNothing().when(mockGroupMemberRepository).deleteById(groupMembers.getId());

        service.exitMember(groupMembers);

        Mockito.verify(mockGroupMemberRepository).deleteById(groupMembers.getId());
    }

    @Test
    void shouldGetGroupsByUser() {
        Mockito.when(mockGroupMemberRepository.findByIdUser(groupMembers.getId().getUser())).thenReturn(groupReadList);

        service.getGroupByUser(groupMembers.getId().getUser());

        Mockito.verify(mockGroupMemberRepository).findByIdUser(groupMembers.getId().getUser());
    }

    @Test
    void shouldGetMemberOfAGroup() {
        Mockito.when(mockGroupMemberRepository.findByIdGroupRead(groupMembers.getId().getGroupRead().getId())).thenReturn(groupMembersList);

        service.getGroupMembers(groupMembers.getId().getGroupRead().getId());

        Mockito.verify(mockGroupMemberRepository).findByIdGroupRead(groupMembers.getId().getGroupRead().getId());
    }
}