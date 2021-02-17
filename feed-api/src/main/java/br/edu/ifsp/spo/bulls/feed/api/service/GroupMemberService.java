package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.GroupInviteTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.feed.api.bean.GroupMemberBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupInvite;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberFull;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupInviteRepository;
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
    private GroupInviteRepository inviteRepository;

    @Autowired
    private UserCommonFeign feign;

    public void putMember(String token, GroupMemberTO membro){
        if(token == null){
            GroupRead group = groupRepository.findById(membro.getGroupId()).orElseGet(null);
            if(group != null) {
                List<GroupMembers> members = repository.findByIdGroupRead(group);
                if(members == null || members.size() == 0) {
                    repository.save(memberBeanUtil.toDomain(membro));
                    return;
                }
            }
        }

        UserTO user = feign.getUserInfo(token);
        GroupRead group = groupRepository.findById(membro.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.GR001));
        if(group.getPrivacy().equals(Privacy.public_all) && user.getId().equals(membro.getUserId())) {
            repository.save(memberBeanUtil.toDomain(membro));
            return;
        }
        GroupMembers member = repository.findMemberByUserId(user.getId(), group.getId());
        if(member != null && (member.getRole().equals(Role.admin) || member.getRole().equals(Role.owner))) {
            repository.save(memberBeanUtil.toDomain(membro));
            return;
        }
        throw new ResourceUnauthorizedException(CodeException.GR004);
    }

    public void exitMember(String token, GroupMemberTO membro){
        UserTO user = feign.getUserInfo(token);
        if(user.getId().equals(membro.getUserId())) {
            repository.deleteById(memberBeanUtil.toDomain(membro).getId());
            return;
        }
        GroupMembers member = repository.findMemberByUserId(user.getId(), membro.getGroupId());
        if(member != null && (member.getRole().equals(Role.admin) || member.getRole().equals(Role.owner))) {
            repository.deleteById(memberBeanUtil.toDomain(membro).getId());
            return;
        }
        throw new ResourceUnauthorizedException(CodeException.GR004);
    }

    public List<GroupRead> getGroupByUser(UUID id) {
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

    public GroupInviteTO invite(String token, GroupInviteTO dto) {
        UserTO user = feign.getUserInfo(token);
        GroupMembers member = repository.findMemberByUserId(user.getId(), dto.getGroupId());
        if(member != null && (member.getRole().equals(Role.admin) || member.getRole().equals(Role.owner))) {
            GroupInvite inv = inviteRepository.findByUserIdAndGroup(dto.getUserId(), dto.getGroupId());
            if (inv != null)
                throw new ResourceUnauthorizedException(CodeException.GR005);
            return memberBeanUtil.toInviteDto(inviteRepository.save(memberBeanUtil.toInvite(dto)));
        }
        throw new ResourceUnauthorizedException(CodeException.GR004);
    }

    public List<GroupInviteTO> getInvites(String token, UUID id){
        UserTO user = feign.getUserInfo(token);
        if(user.getId().equals(id))
            return memberBeanUtil.toInviteDtoList(inviteRepository.findByUserId(id));
        throw new ResourceUnauthorizedException(CodeException.GR005);
    }

    public GroupMemberTO accept(String token, UUID id) {
        UserTO user = feign.getUserInfo(token);
        GroupInvite invite = inviteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.INV001));
        if(user.getId().equals(invite.getUserId())){
            GroupMembers member = new GroupMembers();
            member.setRole(Role.member);
            GroupMemberId memberId = new GroupMemberId();
            memberId.setUser(invite.getUserId());
            memberId.setGroupRead(invite.getGroup());
            member.setId(memberId);
            inviteRepository.delete(invite);
            return memberBeanUtil.toDto(repository.save(member));
        }
        throw new ResourceUnauthorizedException(CodeException.INV002);
    }

    public void refuse(String token, UUID inviteId) {
        UserTO user = feign.getUserInfo(token);
        GroupInvite invite = inviteRepository.findById(inviteId).orElseThrow(() -> new ResourceNotFoundException(CodeException.INV001));
        if(user.getId().equals(invite.getUserId())) {
            inviteRepository.deleteById(inviteId);
            return;
        }
        throw new ResourceUnauthorizedException(CodeException.INV002);
    }
}
