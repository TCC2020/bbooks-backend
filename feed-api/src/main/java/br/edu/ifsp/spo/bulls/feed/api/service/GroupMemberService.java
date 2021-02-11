package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupMemberBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberFull;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberBeanUtil memberBeanUtil;

    @Autowired
    private UserCommonFeign feign;

    public void putMember(GroupMemberTO membro){
        feign.getUserById(membro.getUserId());
        repository.save(memberBeanUtil.toDomain(membro));
    }

    public void exitMember(GroupMemberTO membro){
        repository.deleteById(memberBeanUtil.toDomain(membro).getId());
    }

    public List<GroupRead> getGroupByUser(UUID id) {
        feign.getUserById(id);
        return repository.findByIdUser(id);
    }

    public List<GroupMemberFull> getGroupMembers(UUID id) {
        GroupRead group = groupRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001));
        List<GroupMembers> members = repository.findByIdGroupRead(group);

        List<GroupMemberFull> membersFull = new ArrayList<>();

        members.stream().forEach( a -> membersFull.add(memberBeanUtil.toDtoFull(a)));

        return membersFull;
    }
}
