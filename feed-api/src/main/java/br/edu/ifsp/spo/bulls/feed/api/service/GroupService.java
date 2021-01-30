package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    public Group save(Group group) {
        verifyIfNameIsUnique(group);
        return repository.save(group);
    }

    private void verifyIfNameIsUnique(Group group) {
        if(repository.existsByName(group.getName())){
            throw new ResourceConflictException(CodeException.GR002.getText(), CodeException.GR002);
        }
    }

    public Group update(Group group, UUID groupId) {
        System.out.println(group.toString());
        System.out.println(groupId);
        return repository.findById(groupId).map( group1 -> {
            System.out.println(group1.toString());
            if(!group.getName().equals(group1.getName())){
                verifyIfNameIsUnique(group);
            }
            group1 = group;
            group1.setId(groupId);
            return repository.save(group1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001));

    }

    public Group getById(UUID groupId) {
        return repository.findById(groupId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001));
    }

    public void delete(UUID groupId) {
        repository.deleteById(groupId);
    }
}
