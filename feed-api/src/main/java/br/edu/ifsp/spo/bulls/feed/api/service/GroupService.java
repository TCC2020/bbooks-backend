package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.BookMonthTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupMemberService memberService;

    @Autowired
    private GroupMemberRepository memberRepository;

    @Autowired
    private GroupBeanUtil beanUtil;

    @Autowired
    private UserCommonFeign feign;

    public GroupTO save(GroupTO groupTO) {

        feign.getUserById(groupTO.getUserId());

        verifyIfNameIsUnique(groupTO.getName());
        GroupRead result = repository.save(beanUtil.toDomain(groupTO));

        saveMember(groupTO, result);

        return beanUtil.toDto(result);
    }

    private void saveMember(GroupTO groupTO, GroupRead result) {
        GroupMemberTO member = new GroupMemberTO();
        member.setRole(Role.owner);
        member.setGroupId(result.getId());
        member.setUserId(groupTO.getUserId());
        memberService.putMember(null, member);
    }

    private void verifyIfNameIsUnique(String name) {
        if(repository.existsByName(name)){
            throw new ResourceConflictException(CodeException.GR002.getText(), CodeException.GR002);
        }
    }

    public GroupTO update(String token, GroupTO groupTO, UUID groupId) {
        UserTO requester = feign.getUserInfo(token);
        GroupMembers member = groupMemberRepository.findMemberByUserId(requester.getId(), groupTO.getId());
        if(!(Role.admin.equals(member.getRole()) || Role.owner.equals(member.getRole())))
            throw new ResourceUnauthorizedException(CodeException.GR004);
        GroupRead groupRead = beanUtil.toDomain(groupTO);
        return beanUtil.toDto(repository.findById(groupId).map( group1 -> {
            if(!groupRead.getName().equals(group1.getName())){
                verifyIfNameIsUnique(groupRead.getName());
            }
            group1 = groupRead;
            group1.setId(groupId);
            return repository.save(group1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));

    }

    public GroupTO getById(UUID groupId) {
        return beanUtil.toDto(repository.findById(groupId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));
    }

    public void delete(String token, UUID groupId) {
        UserTO requester = feign.getUserInfo(token);
        UUID owner = groupMemberRepository.findGroupOwner(groupId, Role.owner);
        if(!requester.getId().equals(owner))
            throw new ResourceUnauthorizedException(CodeException.GR003);
        memberRepository.deleteByIdGroupRead(groupId);
        repository.deleteById(groupId);
    }

    public Page<GroupRead> search(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return repository.findByNameContaining(name.toLowerCase(), pageRequest);
    }


}
