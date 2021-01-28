package br.edu.ifsp.spo.bulls.common.api.feign;


import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "users", url = "${feign.users}")
public interface UserCommonFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/users/info")
    UserTO getUserInfo(@RequestHeader("AUTHORIZATION") String token);
}
