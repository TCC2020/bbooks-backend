package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
import br.edu.ifsp.spo.bulls.competition.api.service.ExchangeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exchanges")
@CrossOrigin(origins = "*")
public class ExchangeController {
    private final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private ExchangeService service;

    @ApiOperation(value = "Cria uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não encontrado ou objeto errado")
    })
    @PostMapping
    public ExchangeTO createExchange(@RequestHeader("AUTHORIZATION") String token, @RequestBody ExchangeTO dto) {
        return service.create(token, dto);
    }

    @ApiOperation(value = "Busca uma popostas de troca do usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado")
    })
    @GetMapping("/received/user/{id}")
    public List<ExchangeTO> getByUser(@PathVariable("id") UUID id) {
        return service.getByUser(id);
    }

    @ApiOperation(value = "aceita uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem recebeu"),
            @ApiResponse(code = 404, message = "Anúncio não encontrado"),
    })

    @GetMapping("/sent/user/{id}")
    public List<ExchangeTO> getByUserSent(@PathVariable("id") UUID id) {
        return service.getByUserSent(id);
    }

    @ApiOperation(value = "aceita uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem recebeu"),
            @ApiResponse(code = 404, message = "Anúncio não encontrado"),
    })

    @PutMapping("/accept/{id}")
    public ExchangeTO acceptExchange(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        return service.accept(token, id);
    }

    @ApiOperation(value = "aceita uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem recebeu"),
            @ApiResponse(code = 404, message = "Anúncio não encontrado"),
    })
    @PutMapping("/refuse/{id}")
    public ExchangeTO refuseExchange(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        return service.refuse(token, id);
    }
    @ApiOperation(value = "Delete aceita uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem criou")
    })
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        service.delete(token, id);
    }

}
