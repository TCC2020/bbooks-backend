package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.BookAdTO;
import br.edu.ifsp.spo.bulls.competition.api.service.BookAdService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @ResponseStatus(HttpStatus.CREATED)
    public BookAdTO create(@RequestHeader("AUTHORIZATION") String token, @RequestBody BookAdTO dto){
        logger.info("Criando uma AD para o objeto: " + dto);
        return service.create(token, dto);
    }

    @GetMapping
    public List<BookAdTO> getAll(){
        logger.info("Buscando todas as ads");
        return service.getAds();
    }

    @GetMapping("/{id}")
    public BookAdTO getById(@PathVariable("id") UUID id){
        logger.info("Buscando todas as ads");
        return service.getAdById(id);
    }


    @GetMapping("/user/{id}")
    public List<BookAdTO> getAll(@PathVariable("id") UUID id){
        logger.info("Buscando todas as ads");
        return service.getAdsByUser(id);
    }

    @PutMapping
    public BookAdTO update(@RequestHeader("AUTHORIZATION") String token, @RequestBody BookAdTO dto) {
        return service.update(token, dto);
    }

    @PutMapping("/{id}/image")
    public HttpStatus setImage(@RequestHeader("X-URL") String url, @PathVariable("id") UUID bookAdId, @RequestHeader("token") String token){
        return service.setImage(bookAdId, url, token);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        service.deleteById(token, id);
    }
}
