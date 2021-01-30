package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private GroupBeanUtil beanUtil;

    public GroupTO save(GroupTO groupTO) {
        verifyIfNameIsUnique(groupTO.getName());
         Group result = repository.save(beanUtil.toDomain(groupTO));

        // TODO: Chamar o método do GroupMemberService
        //saveMember(result.getId(), groupTO);

        return beanUtil.toDto(result);
    }

    private void verifyIfNameIsUnique(String name) {
        if(repository.existsByName(name)){
            throw new ResourceConflictException(CodeException.GR002.getText(), CodeException.GR002);
        }
    }

    public GroupTO update(GroupTO groupTO, UUID groupId) {
        Group group = beanUtil.toDomain(groupTO);
        return beanUtil.toDto(repository.findById(groupId).map( group1 -> {
            if(!group.getName().equals(group1.getName())){
                verifyIfNameIsUnique(group.getName());
            }
            group1 = group;
            group1.setId(groupId);
            return repository.save(group1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));

    }

    public GroupTO getById(UUID groupId) {
        return beanUtil.toDto(repository.findById(groupId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));
    }

    public void delete(UUID groupId) {
        repository.deleteById(groupId);
    }
}
