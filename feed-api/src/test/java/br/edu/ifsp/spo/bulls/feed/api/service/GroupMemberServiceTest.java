package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.Cargo;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GroupMemberServiceTest {

    @Autowired
    private GroupMemberService service;

    @MockBean
    private GroupMemberRepository mockGroupMemberRepository;

    private GroupMembers groupMembers;
    private List<GroupMembers> groupMembersList;

    @BeforeEach
    void setUp() {
        GroupMemberId id = new GroupMemberId();
        id.setGroup(UUID.randomUUID());
        id.setUser(UUID.randomUUID());

        groupMembers = new GroupMembers();
        groupMembers.setCargo(Cargo.admin);
        groupMembers.setDate(LocalDateTime.now());
        groupMembers.setId(id);

        groupMembersList = new ArrayList<>();
        groupMembersList.add(groupMembers);
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
        Mockito.when(mockGroupMemberRepository.findByIdUser(groupMembers.getId().getUser())).thenReturn(groupMembersList);

        service.getGroupByUser(groupMembers.getId().getUser());

        Mockito.verify(mockGroupMemberRepository).findByIdUser(groupMembers.getId().getUser());
    }

    @Test
    void shouldGetMemberOfAGroup() {
        Mockito.when(mockGroupMemberRepository.findByIdGroup(groupMembers.getId().getGroup())).thenReturn(groupMembersList);

        service.getGroupMembers(groupMembers.getId().getGroup());

        Mockito.verify(mockGroupMemberRepository).findByIdGroup(groupMembers.getId().getGroup());
    }
}