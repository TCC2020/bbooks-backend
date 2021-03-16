package br.edu.ifsp.spo.bulls.competition.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.ChatIdTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTokenTO;
import br.edu.ifsp.spo.bulls.competition.api.service.ExchangeService;
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
@RequestMapping("/exchanges")
@CrossOrigin(origins = "*")
public class ExchangeController {
    private final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private ExchangeService service;

    @ApiOperation(value = "Cria uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não encontrado ou objeto errado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExchangeTO createExchange(@RequestHeader("AUTHORIZATION") String token, @RequestBody ExchangeTO dto) {
        logger.info("Criando proposta de troca");
        return service.create(token, dto);
    }

    @ApiOperation(value = "Busca uma popostas de troca do usuário")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado")
    })
    @GetMapping("/received/user/{id}")
    public List<ExchangeTO> getByUser(@PathVariable("id") UUID id) {
        logger.info("Buscando propostas recebidas do usuário: " + id);
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
        logger.info("Buscando propostas enviadas do usuário: " + id);
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
        logger.info("Aceitando proposta: " + id);
        return service.accept(token, id);
    }


    @ApiOperation(value = "Busca troca por id")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Retorna a troca"),
            @ApiResponse(code = 404, message = "Troca não encontrada")
    })
    @GetMapping("/{id}")
    public ExchangeTO getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }


    @ApiOperation(value = "Gera um token para trocar os livros")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Retorna token criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem recebeu a proposta"),
            @ApiResponse(code = 404, message = "Troca não encontrada")
    })
    @PutMapping("/generate-token/{id}")
    public ExchangeTokenTO generateToken(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID exchangeId) {
        return service.generateExchangeToken(token, exchangeId);
    }

    @ApiOperation(value = "Gera um token para trocar os livros")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Retorna troca feita"),
            @ApiResponse(code = 401, message = "Token expirado"),
            @ApiResponse(code = 404, message = "Troca não encontrada"),
            @ApiResponse(code = 409, message = "Usuário não é quem enviou a proposta"),
    })
    @PutMapping("/exchange-by-token/{exchangeToken}")
    public ExchangeTO exchangeByToken(@RequestHeader("AUTHORIZATION") String token, @PathVariable("exchangeToken") UUID exchangeToken){
        return service.exchangeByToken(token, exchangeToken);
    }

    @PutMapping("/{exchangeId}/chat/{chatId}")
    public ExchangeTO putChatId(@PathVariable("exchangeId") UUID exchangeId, @PathVariable("chatId") String chatId) {
        return service.putChatId(exchangeId, chatId);
    }

    @GetMapping("/{exchangeId}/chat")
    public ChatIdTO getChatId(@RequestHeader("AUTHORIZATION") String token, @PathVariable("exchangeId") UUID exchangeId) {
        return service.getChatId(token, exchangeId);
    }

    @ApiOperation(value = "aceita uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem recebeu"),
            @ApiResponse(code = 404, message = "Anúncio não encontrado"),
    })
    @PutMapping("/refuse/{id}")
    public ExchangeTO refuseExchange(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        logger.info("Recusando proposta: " + id);
        return service.refuse(token, id);
    }

    @ApiOperation(value = "Cancela uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro cancelado"),
            @ApiResponse(code = 401, message = "Usuário não é quem recebeu"),
            @ApiResponse(code = 404, message = "Anúncio não encontrado"),
    })
    @PutMapping("/cancel/{id}")
    public ExchangeTO cancelExchange(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        logger.info("Cancelando proposta: " + id);
        return service.cancel(token, id);
    }

    @ApiOperation(value = "Delete aceita uma poposta de troca")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Anuncio de livro criado"),
            @ApiResponse(code = 401, message = "Usuário não é quem criou")
    })
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") UUID id) {
        logger.info("Deletando proposta: " + id);
        service.delete(token, id);
    }

}
