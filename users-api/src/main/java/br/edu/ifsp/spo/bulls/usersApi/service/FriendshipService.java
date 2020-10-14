package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.FriendsBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.dto.*;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository repository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private FriendsBeanUtil util;
    @Autowired
    private UserBeanUtil userBeanUtil;

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

    public Friendship getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.FR001.getText(), CodeException.FR001));
    }

    public List<FriendRequestTO> getRequests(String token) {
        Profile profile = profileService.getDomainByToken(token);
        return util.convertToFriendRequests(repository.findPendingRequests(profile.getId()),  profile.getId());
    }

    public HttpStatus accept(String token, Long id) {
        Profile profile = profileService.getDomainByToken(token);
        Friendship friendship = getById(id);
        if(profile.getId() == friendship.getProfile1() || profile.getId() == friendship.getProfile2()) {
            if(!friendship.getStatus().equals(Friendship.FriendshipStatus.pending))
                return HttpStatus.ACCEPTED;
            friendship.setStatus(Friendship.FriendshipStatus.added);
            repository.save(friendship);
            return HttpStatus.OK;
        }
        throw new ResourceConflictException(CodeException.TK001.getText(), CodeException.TK001);
    }

    public HttpStatus reject(String token, Long id) {
        Profile profile = profileService.getDomainByToken(token);
        Friendship friendship = getById(id);
        if(profile.getId() == friendship.getProfile1() || profile.getId() == friendship.getProfile2()) {
            if(!friendship.getStatus().equals(Friendship.FriendshipStatus.pending))
                return HttpStatus.ACCEPTED;
            repository.delete(friendship);
            return HttpStatus.OK;
        }
        throw new ResourceConflictException(CodeException.TK001.getText(), CodeException.TK001);
    }

    public FriendshipTO getFriends(String token) {
        FriendshipTO friends = new FriendshipTO();
        Profile profile = profileService.getDomainByToken(token);
        friends.setProfileId(profile.getId());
        HashSet<Friendship> friendships = repository.findFriendships(profile.getId());
        HashSet<Integer> friendsIds = new HashSet<>();
        friendships.parallelStream().forEach(friendship -> {
            if(friendship.getProfile1() == profile.getId())
                friendsIds.add(friendship.getProfile2());
            else
                friendsIds.add(friendship.getProfile1());
        });
        HashSet<Profile> profiles = profileService.getAllDomainById(friendsIds);
        friends.setFriends((HashSet)profiles.parallelStream().map(profile1 -> userBeanUtil.toUserTO(profile.getUser())).collect(Collectors.toSet()));
        return friends;
    }

    public HttpStatus deleteFriend(String token, AcceptTO dto) {
        Profile profile = profileService.getDomainByToken(token);
        Friendship friendship = getById(dto.getId());
        if(profile.getId() == friendship.getProfile1() || profile.getId() == friendship.getProfile2()) {
            if(friendship.getStatus().equals(Friendship.FriendshipStatus.added)) {
                repository.delete(friendship);
                return HttpStatus.OK;
            }
        }
        throw new ResourceConflictException(CodeException.FR002.getText(), CodeException.FR002);
    }

    public FriendshipTO getFriendsByUsername(String username, String token) {
        FriendshipTO friends = new FriendshipTO();
        Profile profile = profileService.getByUsername(username);
        friends.setProfileId(profile.getId());
        HashSet<Friendship> friendships = repository.findFriendships(profile.getId());
        HashSet<Integer> friendsIds = new HashSet<>();
        friendships.parallelStream().forEach(friendship -> {
            if(friendship.getProfile1() == profile.getId())
                friendsIds.add(friendship.getProfile2());
            else
                friendsIds.add(friendship.getProfile1());
        });
        HashSet<Profile> profiles = profileService.getAllDomainById(friendsIds);
        if(token != null)
            friends.setFriends((HashSet)profiles.parallelStream().map(profile1 -> userBeanUtil.toUserTO(profile1.getUser(), token)).collect(Collectors.toSet()));
        else
            friends.setFriends((HashSet)profiles.parallelStream().map(profile1 -> userBeanUtil.toUserTO(profile1.getUser())).collect(Collectors.toSet()));
        return friends;
    }
}