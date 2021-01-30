package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Autowired
    private GroupService service;

    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group();
        group.setId(UUID.randomUUID());
        group.setUserId(UUID.randomUUID());
        group.setCreationDate(LocalDateTime.now());
        group.setDescription("descricao");
        group.setName("nome");
        group.setPrivacy(Privacy.private_group);
    }

    @Test
    void ShouldSaveGroup() {
        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);
        Mockito.when(mockGroupRepository.existsByName(group.getName())).thenReturn(false);

        Group result = service.save(group);

        assertEquals(group, result);
    }

    @Test
    void ShouldntSaveGroupWhenNameIsAlreadyUsed() {
        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);
        Mockito.when(mockGroupRepository.existsByName(group.getName())).thenReturn(true);

        assertThrows(ResourceConflictException.class,  () -> service.save(group));
    }

    @Test
    void ShouldUpdate() {
        Mockito.when(mockGroupRepository.findById(group.getId())).thenReturn(Optional.ofNullable(group));
        Mockito.when(mockGroupRepository.save(group)).thenReturn(group);
        Mockito.when(mockGroupRepository.existsByName(group.getName())).thenReturn(false);

        Group result = service.update(group, group.getId());

        assertEquals(group, result);
    }

    @Test
    void ShouldntUpdateGroupWhenNameIsAlreadyUsed() {
        Group group1 = group;
        group1.setName("wdwqubduwb");

        Mockito.when(mockGroupRepository.findById(group.getId())).thenReturn(Optional.ofNullable(new Group()));
        Mockito.when(mockGroupRepository.existsByName(group1.getName())).thenReturn(true);

        assertThrows(ResourceConflictException.class,  () -> service.update(group1, group1.getId()));
    }

    @Test
    void getById() {
        Mockito.when(mockGroupRepository.findById(group.getId())).thenReturn(Optional.ofNullable(group));

        Group result = service.getById(group.getId());

        assertNotNull(result);
    }

    @Test
    void delete() {

        service.delete(group.getId());

        Mockito.verify(mockGroupRepository).deleteById(group.getId());
    }
}