package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileTO;
import br.edu.ifsp.spo.bulls.users.api.domain.PublicProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PublicProfileBeanUtil {
    private Logger logger = LoggerFactory.getLogger(PublicProfileBeanUtil.class);

    @Autowired
    private UserBeanUtil userBeanUtil;

    @Autowired
    private ProfileBeanUtil profileBeanUtil;

    public UserPublicProfileTO toDto(PublicProfile domain) {
        UserPublicProfileTO dto = new UserPublicProfileTO();
        try {
            BeanUtils.copyProperties(domain, dto);
            dto.setUser(userBeanUtil.toBaseUserTO(domain.getUser()));
            dto.setFollowers(profileBeanUtil.toDtoList(domain.getFollowers()));
        } catch (Exception e) {
            logger.error("Error while converting PublicProfile to UserPublicProfileTO: " +  e);
        }
        return dto;
    }

    public List<UserPublicProfileTO> toDtoList(List<PublicProfile> list) {
        return list.stream().parallel().map(this::toDto).collect(Collectors.toList());
    }
}
