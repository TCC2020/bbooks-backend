package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private GroupMemberService memberService;

    @Autowired
    private GroupBeanUtil beanUtil;

    public GroupTO save(GroupTO groupTO) {
        // TODO: Verificar se usuário existe antes de salvar o grupo
        verifyIfNameIsUnique(groupTO.getName());
        Group result = repository.save(beanUtil.toDomain(groupTO));

        saveMember(groupTO, result);

        return beanUtil.toDto(result);
    }

    private void saveMember(GroupTO groupTO, Group result) {
        GroupMemberId id = new GroupMemberId();
        id.setGroup(result.getId());
        id.setUser(groupTO.getUserId());
        GroupMembers member = new GroupMembers();
        member.setRole(Role.owner);
        member.setId(id);
        memberService.putMember(member);
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

    public Page<Group> search(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return repository.findByNameContaining(name.toLowerCase(), pageRequest);
    }
}
