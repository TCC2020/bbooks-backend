package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Friendship;
import br.edu.ifsp.spo.bulls.usersApi.dto.FriendRequestTO;
import br.edu.ifsp.spo.bulls.usersApi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendsBeanUtil {
    @Autowired
    private ProfileService profileService;

    public List<FriendRequestTO> convertToFriendRequests(List<Friendship> friendships, int of){
        return friendships.parallelStream().map(friendship -> this.toFriendRequest(friendship, of)).collect(Collectors.toList());
    }

    public FriendRequestTO toFriendRequest(Friendship friendship, int of) {
        FriendRequestTO request = new FriendRequestTO();
        request.setId(friendship.getId());
        request.setAddDate(friendship.getAddDate());
        request.setStatus(friendship.getStatus());
        request.setProfileTO(profileService.getById(friendship.getProfile1() == of ? friendship.getProfile2() : friendship.getProfile1()));
        return request;
    }
}