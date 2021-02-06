package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostBeanUtil {

    private Logger logger = LoggerFactory.getLogger(PostBeanUtil.class);

    @Autowired
    private GroupRepository groupRepository;

    public PostTO toDto(Post post ){
        PostTO postTO = new PostTO();
        try{
            BeanUtils.copyProperties(post, postTO);
        }catch(Exception e) {
            logger.error("Error while converting Post to PostTO: " +  e);
        }

        // TODO: teste aqui
        if(post.getGroup() != null)
            postTO.setGroupId(post.getGroup().getId());

        return postTO;
    }

    public Post toDomain(PostTO postTO ){
        Post post = new Post();
        try{
            BeanUtils.copyProperties(postTO, post );
        }catch(Exception e) {
            logger.error("Error while converting Post to PostTO: " +  e);
        }

        if(postTO.getGroupId() != null)
            post.setGroup(groupRepository.findById(postTO.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));

        return post ;
    }
}
