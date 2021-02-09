package br.edu.ifsp.spo.bulls.feed.api.feign;


import br.edu.ifsp.spo.bulls.common.api.dto.FriendshipStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.GetFriendStatusTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;

@FeignClient(name = "users", url = "${feign.users}")
public interface UserCommonFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/users/info")
    UserTO getUserInfo(@RequestHeader("AUTHORIZATION") String token);

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    UserTO getUserById(@PathVariable("id") UUID id);

    @RequestMapping(method = RequestMethod.GET, value = "/profiles/token/{token}")
    ProfileTO getProfileByToken(@PathParam("token") String token);

    @RequestMapping(method = RequestMethod.POST, value = "/friends/status")
    FriendshipStatusTO getFriendshipStatusTO(@RequestBody GetFriendStatusTO dto);
}
