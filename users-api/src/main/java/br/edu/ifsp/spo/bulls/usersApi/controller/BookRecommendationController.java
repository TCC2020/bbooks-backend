package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.BookRecommendationTO;
import br.edu.ifsp.spo.bulls.usersApi.service.BookRecommendationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/book-recommendation", produces="application/json")
@CrossOrigin(origins = "*")
public class BookRecommendationController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookRecommendationService service;

    @ApiOperation(value = "Enviar uma recomendação")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a recomendação feita"),
            @ApiResponse(code = 409, message = "Recomendação já existe na base"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public BookRecommendationTO save(@RequestBody BookRecommendationTO recommendationTO){
        logger.info("Cadastrando novo Livro " + recommendationTO);
        return service.save(recommendationTO);
    }
}
