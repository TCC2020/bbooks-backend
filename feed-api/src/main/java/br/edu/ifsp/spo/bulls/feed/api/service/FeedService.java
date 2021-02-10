package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.FriendshipStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.GetFriendStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    @Autowired
    private PostRepository repository;

    @Autowired
    private UserCommonFeign feign;

    public Page<PostTO> getFeed(String token, int page, int size) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        ProfileTO profileTO = feign.getProfileByToken(tokenValue);
        return repository.findFeedByRequesterId(profileTO.getId(), pageable);
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
}
