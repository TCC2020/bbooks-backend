package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.Cargo;
import br.edu.ifsp.spo.bulls.feed.api.domain.Group;
import br.edu.ifsp.spo.bulls.feed.api.dto.GroupTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupBeanUtil {

    private Logger logger = LoggerFactory.getLogger(GroupBeanUtil.class);

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public GroupTO toDto(Group group ){
        GroupTO groupTO = new GroupTO();
        try{
            BeanUtils.copyProperties(group, groupTO);
        }catch(Exception e) {
            logger.error("Error while converting Group to GroupTO: " +  e);
        }
        groupTO.setUserId(groupMemberRepository.findGroupOwner(group.getId(), Cargo.owner));

        return groupTO;
    }

    public Group toDomain(GroupTO groupTO ){
        Group group = new Group();
        try{
            BeanUtils.copyProperties(groupTO, group);
        }catch(Exception e) {
            logger.error("Error while converting GroupTO to Group: " +  e);
        }

        return group;
    }
}
