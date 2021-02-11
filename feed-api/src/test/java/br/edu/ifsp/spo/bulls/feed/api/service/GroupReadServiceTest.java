package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class, classes = {GroupService.class, GroupBeanUtil.class})
public class GroupReadServiceTest {

    @MockBean
    private GroupRepository mockGroupRepository;

    @MockBean
    private GroupMemberRepository mockGroupMemberRepository;

    @MockBean
    private GroupMemberService mockGroupMemService;

    @Autowired
    private GroupService service;

    @MockBean
    private UserCommonFeign feign;

    @Autowired
    private GroupBeanUtil beanUtil;

    private GroupTO groupTO;
    private GroupRead groupRead;

    @BeforeEach
    void setUp() {
        groupTO = new GroupTO();
        groupTO.setId(UUID.randomUUID());
        groupTO.setUserId(UUID.randomUUID());
        groupTO.setCreationDate(LocalDateTime.now());
        groupTO.setDescription("descricao");
        groupTO.setName("nome");
        groupTO.setPrivacy(Privacy.private_group);

        groupRead = beanUtil.toDomain(groupTO);
    }

    @Test
    void shouldSaveGroup() {

        GroupMemberTO member = new GroupMemberTO();
        member.setRole(Role.owner);
        member.setGroupId(groupTO.getId());
        member.setUserId(groupTO.getUserId());


        Mockito.when(mockGroupRepository.save(groupRead)).thenReturn(groupRead);
        Mockito.when(mockGroupRepository.existsByName(groupTO.getName())).thenReturn(false);
        Mockito.when(mockGroupMemberRepository.findGroupOwner(groupRead.getId(), Role.owner)).thenReturn(groupTO.getUserId());
        Mockito.when(feign.getUserById(groupTO.getUserId())).thenReturn(new UserTO());
        Mockito.doNothing().when(mockGroupMemService).putMember(member);


        GroupTO result = service.save(groupTO);

        assertEquals(groupTO, result);
    }

    @Test
    void shouldntSaveGroupWhenNameIsAlreadyUsed() {
        Mockito.when(mockGroupRepository.save(groupRead)).thenReturn(groupRead);
        Mockito.when(mockGroupRepository.existsByName(groupTO.getName())).thenReturn(true);
        Mockito.when(mockGroupMemberRepository.findGroupOwner(groupRead.getId(), Role.owner)).thenReturn(groupTO.getUserId());

        assertThrows(ResourceConflictException.class,  () -> service.save(groupTO));
    }

    @Test
    void shouldUpdate() {
        Mockito.when(mockGroupRepository.findById(groupTO.getId())).thenReturn(Optional.ofNullable(groupRead));
        Mockito.when(mockGroupRepository.save(groupRead)).thenReturn(groupRead);
        Mockito.when(mockGroupRepository.existsByName(groupTO.getName())).thenReturn(false);
        Mockito.when(mockGroupMemberRepository.findGroupOwner(groupRead.getId(), Role.owner)).thenReturn(groupTO.getUserId());

        GroupTO result = service.update(groupTO, groupTO.getId());

        assertEquals(groupTO, result);
    }

    @Test
    void shouldntUpdateGroupWhenNameIsAlreadyUsed() {
        GroupTO group1 = groupTO;
        group1.setName("wdwqubduwb");

        Mockito.when(mockGroupRepository.findById(groupTO.getId())).thenReturn(Optional.ofNullable(new GroupRead()));
        Mockito.when(mockGroupRepository.existsByName(group1.getName())).thenReturn(true);

        assertThrows(ResourceConflictException.class,  () -> service.update(group1, group1.getId()));
    }

    @Test
    void getById() {
        Mockito.when(mockGroupRepository.findById(groupTO.getId())).thenReturn(Optional.ofNullable(groupRead));

        GroupTO result = service.getById(groupTO.getId());

        assertNotNull(result);
    }

    @Test
    void delete() {
        service.delete(groupTO.getId());
        Mockito.verify(mockGroupRepository).deleteById(groupTO.getId());
    }


    @Test
    void getByName() {
        PageRequest pageRequest = PageRequest.of(
                0,
                1,
                Sort.Direction.ASC,
                "id");
        Mockito.when(mockGroupRepository.findByNameContaining("teste", pageRequest)).thenReturn(Page.empty());

        service.search("teste", 0, 1);

        Mockito.verify(mockGroupRepository).findByNameContaining("teste", pageRequest);

    }
}