package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.Friendship;
import br.edu.ifsp.spo.bulls.users.api.dto.FriendRequestTO;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendsBeanUtil {
    @Autowired
    private ProfileService profileService;

    public List<FriendRequestTO> convertToFriendRequests(List<Friendship> friendships, int of){
        return friendships.parallelStream().filter(friendship -> !Friendship.FriendshipStatus.added.equals(friendship.getStatus())).map(friendship -> this.toFriendRequest(friendship, of)).collect(Collectors.toList());
    }

    public FriendRequestTO toFriendRequest(Friendship friendship, int of) {
        FriendRequestTO request = new FriendRequestTO();
        if(friendship.getStatus().equals(Friendship.FriendshipStatus.added))
            return null;
        request.setId(friendship.getId());
        request.setAddDate(friendship.getAddDate());
        request.setStatus(friendship.getProfile1() == of ? Friendship.FriendshipStatus.sent : Friendship.FriendshipStatus.received);
        request.setProfileTO(profileService.getById(friendship.getProfile1() == of ? friendship.getProfile2() : friendship.getProfile1()));
        return request;
    }
}
