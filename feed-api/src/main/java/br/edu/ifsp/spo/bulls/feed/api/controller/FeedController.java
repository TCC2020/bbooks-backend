package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.service.FeedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/feed", produces="application/json")
@CrossOrigin(origins = "*")
public class FeedController {
    @Autowired
    private FeedService service;

    private final Logger logger = LoggerFactory.getLogger(FeedController.class);

    @ApiOperation(value = "Retorna o feed do usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Feed String")
    })

    @GetMapping
    public Page<PostTO> getOwnFeed(@RequestHeader("AUTHORIZATION") String token, @RequestParam int page, @RequestParam int size) {
        logger.info("Requisitando feed do usuário :" + token);
        return service.getFeed(token, page, size);
    }
}