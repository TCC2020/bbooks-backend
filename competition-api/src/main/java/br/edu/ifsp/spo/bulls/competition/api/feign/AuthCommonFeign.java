package br.edu.ifsp.spo.bulls.competition.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "auth", url = "${feign.users}")
public interface AuthCommonFeign {
    /**
            * ESSE USER E DO SECURITY
     * **/
    @RequestMapping(method = RequestMethod.GET, value = "/auth/{token}")
    Optional<User> findByToken(@PathVariable("token") String token);
}
