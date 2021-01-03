package br.edu.ifsp.spo.bulls.users.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/competitions", produces="application/json")
@CrossOrigin(origins = "*")
public class FeedController {

    private final Logger logger = LoggerFactory.getLogger(FeedController.class);

    @ApiOperation(value = "Retorna string teste")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competition String")
    })
    @GetMapping
    public String getFeed() {
        logger.info("Requisitando competições");
        return "Competition is working!";
    }
}