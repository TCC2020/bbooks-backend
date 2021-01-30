package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.Cargo;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GroupServiceTest {

    @MockBean
    private GroupRepository mockGroupRepository;
    @MockBean
    private GroupMemberRepository mockGroupMemberRepository;

    @Autowired
    private GroupService service;

    @Autowired
    private GroupBeanUtil beanUtil;

    private GroupTO groupTO;
    private Group group;

    @BeforeEach
    void setUp() {
        groupTO = new GroupTO();
        groupTO.setId(UUID.randomUUID());
        groupTO.setUserId(UUID.randomUUID());
        groupTO.setCreationDate(LocalDateTime.now());
        groupTO.setDescription("descricao");
        groupTO.setName("nome");
        groupTO.setPrivacy(Privacy.private_group);

        group = beanUtil.toDomain(groupTO);
    }

    @Test
    void ShouldSaveGroup() {
        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);
        Mockito.when(mockGroupRepository.existsByName(groupTO.getName())).thenReturn(false);
        Mockito.when(mockGroupMemberRepository.findGroupOwner(group.getId(), Cargo.owner)).thenReturn(groupTO.getUserId());

        GroupTO result = service.save(groupTO);

        assertEquals(groupTO, result);
    }

    @Test
    void ShouldntSaveGroupWhenNameIsAlreadyUsed() {
        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);
        Mockito.when(mockGroupRepository.existsByName(groupTO.getName())).thenReturn(true);
        Mockito.when(mockGroupMemberRepository.findGroupOwner(group.getId(), Cargo.owner)).thenReturn(groupTO.getUserId());

        assertThrows(ResourceConflictException.class,  () -> service.save(groupTO));
    }

    @Test
    void ShouldUpdate() {
        Mockito.when(mockGroupRepository.findById(groupTO.getId())).thenReturn(Optional.ofNullable(group));
        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);
        Mockito.when(mockGroupRepository.existsByName(groupTO.getName())).thenReturn(false);
        Mockito.when(mockGroupMemberRepository.findGroupOwner(group.getId(), Cargo.owner)).thenReturn(groupTO.getUserId());

        GroupTO result = service.update(groupTO, groupTO.getId());

        assertEquals(groupTO, result);
    }

    @Test
    void ShouldntUpdateGroupWhenNameIsAlreadyUsed() {
        GroupTO group1 = groupTO;
        group1.setName("wdwqubduwb");

        Mockito.when(mockGroupRepository.findById(groupTO.getId())).thenReturn(Optional.ofNullable(new Group()));
        Mockito.when(mockGroupRepository.existsByName(group1.getName())).thenReturn(true);

        assertThrows(ResourceConflictException.class,  () -> service.update(group1, group1.getId()));
    }

    @Test
    void getById() {
        Mockito.when(mockGroupRepository.findById(groupTO.getId())).thenReturn(Optional.ofNullable(group));

        GroupTO result = service.getById(groupTO.getId());

        assertNotNull(result);
    }

    @Test
    void delete() {

        service.delete(groupTO.getId());

        Mockito.verify(mockGroupRepository).deleteById(groupTO.getId());
    }
}