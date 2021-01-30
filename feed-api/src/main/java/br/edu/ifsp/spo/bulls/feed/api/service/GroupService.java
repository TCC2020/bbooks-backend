package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
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
        //TODO: testar método

        return repository.save(group);
    }

    public Group update(Group group, UUID groupId) {
        //TODO: testar
        return repository.findById(groupId).map( group1 -> {
            group1 = group;
            group1.setId(groupId);
            return repository.save(group1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001));

    }

    public Group getById(UUID groupId) {
        //TODO: testar método
        return repository.findById(groupId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001));
    }

    public void delete(UUID groupId) {
        //TODO: Testar método
        repository.deleteById(groupId);
    }
}
