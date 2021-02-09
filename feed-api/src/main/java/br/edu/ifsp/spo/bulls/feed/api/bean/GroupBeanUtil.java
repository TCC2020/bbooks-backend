package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
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

    public GroupTO toDto(GroupRead groupRead){
        GroupTO groupTO = new GroupTO();
        try{
            BeanUtils.copyProperties(groupRead, groupTO);
        }catch(Exception e) {
            logger.error("Error while converting Group to GroupTO: " +  e);
        }
        groupTO.setUserId(groupMemberRepository.findGroupOwner(groupRead.getId(), Role.owner));

        return groupTO;
    }

    public GroupRead toDomain(GroupTO groupTO ){
        GroupRead groupRead = new GroupRead();
        try{
            BeanUtils.copyProperties(groupTO, groupRead);
        }catch(Exception e) {
            logger.error("Error while converting GroupTO to Group: " +  e);
        }

        return groupRead;
    }
}
