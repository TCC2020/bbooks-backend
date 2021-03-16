package br.edu.ifsp.spo.bulls.users.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;

@FeignClient(name = "googleapi", url = "https://www.googleapis.com/books/v1")
public interface GoogleAPiFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/volumes")
    LinkedHashMap getBooks(@RequestParam("maxResults") int maxResults, @RequestParam("q") String q, @RequestParam("startIndex") int startIndex);
}
