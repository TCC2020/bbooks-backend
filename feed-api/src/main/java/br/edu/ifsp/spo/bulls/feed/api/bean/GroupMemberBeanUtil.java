package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMemberId;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberFull;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupMemberTO;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupMemberBeanUtil {
    private Logger logger = LoggerFactory.getLogger(GroupBeanUtil.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserCommonFeign feign;

    public GroupMembers toDomain(GroupMemberTO memberTO){
        GroupMembers groupMembers = new GroupMembers();

        try{
            BeanUtils.copyProperties(memberTO, groupMembers);
        }catch(Exception e) {
            logger.error("Error while converting GroupMemberTO to GroupMembers: " +  e);
        }

        GroupMemberId id = new GroupMemberId();
        id.setUser(memberTO.getUserId());
        id.setGroupRead(groupRepository.findById(memberTO.getGroupId())
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));


        groupMembers.setId(id);

        return groupMembers;
    }

    public GroupMemberTO toDto(GroupMembers groupMembers){
        GroupMemberTO memberTO = new GroupMemberTO();

        try{
            BeanUtils.copyProperties(groupMembers, memberTO);
        }catch(Exception e) {
            logger.error("Error while converting GroupMembers to GroupMemberTO: " +  e);
        }

        memberTO.setUserId(groupMembers.getId().getUser());
        memberTO.setGroupId(groupMembers.getId().getGroupRead().getId());

        return memberTO;
    }

    public GroupMemberFull toDtoFull(GroupMembers groupMembers){
        GroupMemberFull memberTO = new GroupMemberFull();

        try{
            BeanUtils.copyProperties(groupMembers, memberTO);
            memberTO.setUser(feign.getUserById(groupMembers.getId().getUser()));
        }catch(Exception e) {
            logger.error("Error while converting GroupMembers to GroupMemberTO: " +  e);
        }


        memberTO.setGroupId(groupMembers.getId().getGroupRead().getId());

        return memberTO;
    }
}
