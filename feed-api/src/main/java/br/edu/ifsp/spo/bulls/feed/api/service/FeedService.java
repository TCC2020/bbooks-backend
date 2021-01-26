package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.domain.Friendship;
import br.edu.ifsp.spo.bulls.common.api.domain.User;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.repository.FriendshipCommonRepository;
import br.edu.ifsp.spo.bulls.common.api.repository.UserCommonRepository;
import br.edu.ifsp.spo.bulls.feed.api.client.Client;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedService {
    @Autowired
    private PostRepository repository;

    @Autowired
    private UserCommonRepository userCommonRepository;

    @Autowired
    private FriendshipCommonRepository friendshipCommonRepository;

    @Autowired
    private Client client;

    public Page<PostTO> getFeed(String token, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        ProfileTO profileTO = client.getProfileByToken(token);
        return repository.findFeedByRequesterId(profileTO.getId(), pageable);
    }

    public Page<PostTO> getProfileFeed(String token, int profileId,  int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        ProfileTO profileTO = client.getProfileByToken(token);
        Optional<Friendship> friendship = friendshipCommonRepository
                .hasFriendship(profileTO.getId(), profileId);
        if(friendship.isPresent() && friendship.get().getStatus().equals(Friendship.FriendshipStatus.added)){
            return repository.findFeedByRequesterId(profileTO.getId(), pageable);
        }
        return repository.findFeedByRequesterIdPublic(profileTO.getId(), profileId, pageable);
    }
}
