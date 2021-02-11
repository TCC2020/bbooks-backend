package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.service.FeedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;

import java.util.List;


@RestController
@RequestMapping(value = "/feed", produces="application/json")
@CrossOrigin(origins = "*")
public class FeedController {

    private final Logger logger = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    private FeedService service;

    @ApiOperation(value = "Retorna o feed do usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Feed String")
    })
    @GetMapping
    public List<PostTO> getFeed(@RequestHeader(value = "AUTHORIZATION") String token, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando feed");
        return service.getFeed(token, page, size);
    }

    @GetMapping("/{profileId}")
    public Page<PostTO> getPersonFeed(@RequestHeader(value = "AUTHORIZATION") String token, @PathVariable("profileId") int profileId, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando feed do perfil: " + profileId);
        Page<PostTO> res = service.getProfileFeed(token, profileId, page, size);
        logger.info("Objeto encontrado: " + res);
        return res;
    }
}