package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.FriendshipStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.GetFriendStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.feed.api.bean.PostBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupMembers;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.enums.MemberStatus;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupMemberRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FeedService {
    @Autowired
    private PostRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private UserCommonFeign feign;

    @Autowired
    private PostBeanUtil utils;

    public List<PostTO> getFeed(String token, int page, int size) {
        ProfileTO profileTO = feign.getProfileByToken(StringUtils.removeStart(token, "Bearer").trim());
        return utils.toDtoList(repository.findFeedByRequesterId(profileTO.getId()));
    }

    public Page<PostTO> getProfileFeed(String token, int profileId,  int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();

        ProfileTO profileTO = feign.getProfileByToken(tokenValue);
        if(profileTO != null && profileId == profileTO.getId())
            return repository.findByProfileId(profileId, TypePost.post, pageable);
        GetFriendStatusTO getStatus = new GetFriendStatusTO();
        getStatus.setProfileId(profileTO.getId());
        getStatus.setProfileFriendId(profileId);
        try {
            FriendshipStatusTO friendship = feign.getFriendshipStatusTO(getStatus);
            if("added".equalsIgnoreCase(friendship.getStatus()))
                return repository.findByProfileId(profileId, TypePost.post, pageable);
        } catch (Exception e) {
            System.out.println(e);
        }
        return repository.findFeedByRequesterIdPublic(profileId, pageable);
    }

    public List<PostTO> getGroupFeed(String token, UUID groupId) {
        UserTO user = feign.getUserInfo(token);
        GroupRead group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.GR001));
        if(!group.getPrivacy().equals(Privacy.private_group))
            return utils.toDtoList(repository.findByGroupId(groupId));
        GroupMembers member = groupMemberRepository.findMemberByUserId(user.getId(), groupId);
        if (member != null && MemberStatus.accepted.equals(member.getStatus()))
            return utils.toDtoList(repository.findByGroupId(groupId));
        throw new ResourceUnauthorizedException(CodeException.GR005);
    }
}
