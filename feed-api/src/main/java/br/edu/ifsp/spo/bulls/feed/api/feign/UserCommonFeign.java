package br.edu.ifsp.spo.bulls.feed.api.feign;


import br.edu.ifsp.spo.bulls.common.api.dto.FriendshipStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.GetFriendStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.dto.profile.BaseProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "users", url = "${feign.users}")
public interface UserCommonFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/users/info")
    UserTO getUserInfo(@RequestHeader("AUTHORIZATION") String token);

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    UserTO getUserById(@PathVariable("id") UUID id);

    @RequestMapping(method = RequestMethod.GET, value = "/profiles/token/{token}")
    ProfileTO getProfileByToken(@PathVariable("token") String token);

    @RequestMapping(method = RequestMethod.GET, value = "/profiles/basic/{id}")
    BaseProfileTO getBaseProfileById(@PathVariable("id") int profileId);

    @RequestMapping(method = RequestMethod.POST, value = "/friends/status")
    FriendshipStatusTO getFriendshipStatusTO(@RequestBody GetFriendStatusTO dto);

    @RequestMapping(method = RequestMethod.GET, value = "/users/profile/{profileId}")
    UserTO getUserByProfileId(@PathVariable("profileId") int profileId);

    @RequestMapping(method = RequestMethod.GET, value = "/users/public-profiles/{id}")
    UserPublicProfileTO getPublicProfile(@PathVariable("id") UUID pageId);
}
