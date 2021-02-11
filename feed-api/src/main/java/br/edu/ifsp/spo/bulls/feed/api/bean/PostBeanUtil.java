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

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PostTO> toDtoList(List<Post> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}
