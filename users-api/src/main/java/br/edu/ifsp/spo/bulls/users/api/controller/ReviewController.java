package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import br.edu.ifsp.spo.bulls.users.api.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@RestController
@RequestMapping(value = "/review", produces="application/json")
@CrossOrigin(origins = "*")
public class ReviewController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ReviewService service;

    @ApiOperation(value = "Retorna uma resenha por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a resenha"),
            @ApiResponse(code = 404, message = "Resenha não encontrada")
    })
    @GetMapping(value = "/{reviewId}")
    public ReviewTO getOne(@PathVariable UUID reviewId, @RequestHeader(value = "AUTHORIZATION") String token){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Acessar dados de uma resenha " + reviewId);
        return service.getById(reviewId, tokenValue);
    }

    @ApiOperation(value = "Retorna uma resenha por livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de resenhas"),
            @ApiResponse(code = 404, message = "Livro não encontrado")
    })
    @GetMapping("/book/{bookId}")
    public Page<ReviewTO> getAllByBook(@PathVariable int bookId,@RequestParam int page, @RequestParam int size, @RequestHeader(value = "AUTHORIZATION") String token){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Acessar resenhas por livro " + bookId);
        return service.getAllByBook(bookId, tokenValue, page, size);
    }

    @ApiOperation(value = "Retorna uma resenha por livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de resenhas"),
            @ApiResponse(code = 404, message = "Livro não encontrado")
    })
    @GetMapping("/google-book/{googleBookId}")
    public Page<ReviewTO> getAllByGoogleBook(@PathVariable String googleBookId, @RequestParam int page, @RequestParam int size, @RequestHeader(value = "AUTHORIZATION") String token){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Acessar resenhas por livro " + googleBookId);
        return service.getAllByGoogleBook(googleBookId, tokenValue, page,size);
    }

    @ApiOperation(value = "Salvar uma resenha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a resenha cadastrada"),
            @ApiResponse(code = 404, message = "Livro não encontrado")
    })
    @PostMapping()
    public ReviewTO postReview(@RequestBody ReviewTO reviewTO, @RequestHeader(value = "AUTHORIZATION") String token){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Cadastrando resenha");
        return service.save(reviewTO, tokenValue);
    }

    @ApiOperation(value = "Editar uma resenha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a resenha editada"),
            @ApiResponse(code = 404, message = "Resenha encontrada")
    })
    @PutMapping("/{reviewId}")
    public ReviewTO putReview(@PathVariable UUID reviewId ,@RequestBody ReviewTO reviewTO, @RequestHeader(value = "AUTHORIZATION") String token){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Editar resenha " + reviewId);
        return service.update(reviewId, reviewTO, tokenValue);
    }

    @ApiOperation(value = "Deletar resenha por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resenha deletada")
    })
    @DeleteMapping("/{reviewId}")
    public void deleteById(@PathVariable UUID reviewId, @RequestHeader(value = "AUTHORIZATION") String token){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        logger.info("Acessr dados de uma resenha " + reviewId);
        service.delete(reviewId, tokenValue);
    }
}
