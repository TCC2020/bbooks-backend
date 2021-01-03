package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.dto.FeedTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/feed", produces="application/json")
@CrossOrigin(origins = "*")
public class FeedController {

    private final Logger logger = LoggerFactory.getLogger(FeedController.class);

    @ApiOperation(value = "Retorna o feed do usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Feed String")
    })
    @GetMapping
    public FeedTO getFeed() {
        logger.info("Requisitando feed");
        return new FeedTO("Feed is being implemented");
    }
}