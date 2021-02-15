package br.edu.ifsp.spo.bulls.competition.api.feign;

import br.edu.ifsp.spo.bulls.common.api.dto.BookTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.websocket.server.PathParam;
import java.util.UUID;

@FeignClient(name = "users", url = "${feign.users}")
public interface UserCommonFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/users/info")
    UserTO getUserInfo(@RequestHeader("AUTHORIZATION") String token);

    @RequestMapping(method = RequestMethod.GET, value = "/books/{id}")
    BookTO getBook(@PathParam("id")int id);

    @RequestMapping(method = RequestMethod.GET, value = "/profiles/{id}")
    ProfileTO getProfile(@PathParam("id") UUID id);
}