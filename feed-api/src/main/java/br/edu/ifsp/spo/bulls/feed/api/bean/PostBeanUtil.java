package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.enums.ReactionType;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.domain.Reactions;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.dto.ReactionsByType;
import br.edu.ifsp.spo.bulls.feed.api.dto.ReactionsTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostBeanUtil {

    @Autowired
    private UserCommonFeign feign;

    @Autowired
    private SurveyBeanUtil surveyBeanUtil;

    private Logger logger = LoggerFactory.getLogger(PostBeanUtil.class);

    @Autowired
    private GroupRepository groupRepository;

    // TODO: Retornar último voto de enquete
    public PostTO toDto(Post post){
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
        postTO.setReactions(reactionsToReactionsTO(post.getReactions()));

        return postTO;
    }

    public ReactionsTO reactionsToReactionsTO(List<Reactions> reactionsDomain) {
        ReactionsTO reactions = new ReactionsTO();
        if(reactionsDomain == null || reactionsDomain.size() == 0)
            return reactions;
        reactions.setCount(reactionsDomain.size());
        reactions.setLikes(toReactionsByType(ReactionType.like, filterByType(ReactionType.like, reactionsDomain)));
        reactions.setDislike(toReactionsByType(ReactionType.dislike, filterByType(ReactionType.dislike, reactionsDomain)));
        reactions.setLoved(toReactionsByType(ReactionType.loved, filterByType(ReactionType.loved, reactionsDomain)));
        reactions.setHilarius(toReactionsByType(ReactionType.hilarius, filterByType(ReactionType.hilarius, reactionsDomain)));
        reactions.setSurprised(toReactionsByType(ReactionType.surprised, filterByType(ReactionType.surprised, reactionsDomain)));
        reactions.setSad(toReactionsByType(ReactionType.sad, filterByType(ReactionType.sad, reactionsDomain)));
        reactions.setHated(toReactionsByType(ReactionType.hated, filterByType(ReactionType.hated, reactionsDomain)));
        return reactions;
    }

    public List<Reactions> filterByType(ReactionType type, List<Reactions> reactions) {
        return reactions
                .stream().filter(reaction -> reaction.getReaction().equals(type))
                .collect(Collectors.toList());
    }

    public ReactionsByType toReactionsByType(ReactionType type, List<Reactions> reactionsDomain) {
        ReactionsByType reactionsByType = new ReactionsByType();
        reactionsByType.setType(type);
        reactionsByType.setCount(reactionsDomain.size());
        reactionsByType.setProfiles(reactionsDomain
                .stream().parallel()
                .map(reaction -> feign.getBaseProfileById(reaction.getProfileId()))
                .collect(Collectors.toList()));
        return reactionsByType;
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

    public Page<PostTO> toDtoPage(Page<Post> list) {
        return list.map(this::toDto);
    }
}
