package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostBeanUtil {

    @Autowired
    private UserCommonFeign feign;

    @Autowired
    private SurveyBeanUtil surveyBeanUtil;

    @Autowired
    private PostRepository postRepository;

    private Logger logger = LoggerFactory.getLogger(PostBeanUtil.class);

    @Autowired
    private GroupRepository groupRepository;

    public PostTO toDto(Post post ){
        PostTO postTO = new PostTO();
        try{
            BeanUtils.copyProperties(post, postTO);
            postTO.setUser(feign.getUserByProfileId(post.getProfileId()));
        }catch(Exception e) {
            logger.error("Error while converting Post to PostTO: " +  e);
        }
        if(post.getSurvey() != null)
            postTO.setSurvey(surveyBeanUtil.toDto(post.getSurvey()));
        // TODO: teste aqui
        if(post.getGroup() != null)
            postTO.setGroupId(post.getGroup().getId());

        return postTO;
    }

    public Post toDomain(PostTO postTO) {
        Post post = new Post();
        try {
            BeanUtils.copyProperties(postTO, post);
            if(postTO.getSurvey() != null)
                post.setSurvey(surveyBeanUtil.toDomain(postTO.getSurvey()));
        } catch (Exception e) {
            logger.error("Error while converting Post to PostTO: " + e);
        }

        if (postTO.getGroupId() != null)
            post.setGroup(groupRepository.findById(postTO.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001)));

        return post;
    }

    public List<PostTO> toDtoList(List<Post> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<PostTO> toDtoList(Page<PostTO> list) {

        List<PostTO> postSurveyList = new ArrayList<>();
        List<PostTO> postList= list.getContent();

        for (PostTO postTO: postList ) {
            Post post = postRepository.findById(postTO.getId()).orElseThrow(() -> new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));
            postTO.setSurvey(surveyBeanUtil.toDto(post.getSurvey()));
            postSurveyList.add(postTO);
        }

        list.setContent(postSurveyList);
        return list;
    }
}
