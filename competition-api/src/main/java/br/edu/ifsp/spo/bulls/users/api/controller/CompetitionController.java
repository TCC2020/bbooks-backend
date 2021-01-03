package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.CompetitionTO;
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
@RequestMapping(value = "/competitions", produces="application/json")
@CrossOrigin(origins = "*")
public class CompetitionController {

    private final Logger logger = LoggerFactory.getLogger(CompetitionController.class);

    @ApiOperation(value = "Retorna string teste")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Competition String")
    })
    @GetMapping
    public CompetitionTO getFeed() {
        logger.info("Requisitando competições");
        return new CompetitionTO("Competition is working!");
    }
}