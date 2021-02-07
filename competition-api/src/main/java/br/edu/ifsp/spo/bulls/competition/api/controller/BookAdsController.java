package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.BookAdTO;
import br.edu.ifsp.spo.bulls.competition.api.service.BookAdService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-ads")
@CrossOrigin(origins = "*")
public class BookAdsController {
    private final Logger logger = LoggerFactory.getLogger(BookAdsController.class);

    @Autowired
    private BookAdService service;

    @ApiOperation(value = "Retorna string teste")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não encontrado")
    })
    @PostMapping
    public BookAdTO create(@RequestHeader("AUTHORIZATION") String token, BookAdTO dto){
        logger.info("Criando uma AD para o objeto: " + dto);
        return service.create(token, dto);
    }

    @GetMapping
    public List<BookAdTO> getAll(){
        logger.info("Buscando todas as ads");
        return service.getAds();
    }
}
