package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.FriendsBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.dto.FriendRequestTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.FriendTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository repository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private FriendsBeanUtil util;

    public HttpStatus add(FriendTO friendTO, String token) {
        Profile from = profileService.getDomainByToken(token);
        Profile to = profileService.getDomainById(friendTO.getId());
        Friendship friendship = new Friendship();
        friendship.setProfile1(from.getId());
        friendship.setProfile2(to.getId());
        friendship.setAddDate(LocalDateTime.now());
        friendship.setStatus(Friendship.FriendshipStatus.pending);
        repository.save(friendship);
        return HttpStatus.CREATED;
    }

    public List<FriendRequestTO> getRequests(String token) {
        Profile profile = profileService.getDomainByToken(token);
        return util.convertToFriendRequests(repository.findPendingRequests(profile.getId()),  profile.getId());
    }
}