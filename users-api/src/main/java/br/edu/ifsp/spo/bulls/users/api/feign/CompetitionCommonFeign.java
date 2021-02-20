package br.edu.ifsp.spo.bulls.users.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(name = "competition", url = "${feign.competition}")
public interface CompetitionCommonFeign {

    @RequestMapping(method = RequestMethod.PUT, value = "/book-ads/{id}/book-ads/{token}")
    HttpStatus updateBookAdImage(@PathVariable("token") String token, @RequestHeader("X-URL") String url, @PathVariable("id") UUID bookAdId);
}