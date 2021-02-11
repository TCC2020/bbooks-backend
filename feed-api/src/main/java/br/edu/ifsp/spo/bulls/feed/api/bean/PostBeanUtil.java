package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostBeanUtil {

    @Autowired
    private UserCommonFeign feign;

    private Logger logger = LoggerFactory.getLogger(PostBeanUtil.class);

    public PostTO toDto(Post post ){
        PostTO postTO = new PostTO();
        try{
            BeanUtils.copyProperties(post, postTO);
            postTO.setUser(feign.getUserByProfileId(post.getProfileId()));
        }catch(Exception e) {
            logger.error("Error while converting Post to PostTO: " +  e);
        }

        return postTO;
    }

    public Page<PostTO> toPageDto(Page<Post> page) {
        if (page == null)
            return null;
        Page<PostTO> dtos = page.map(this::toDto);
        return dtos;
    }
}
