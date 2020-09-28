package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.service.UserBooksService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookcases", produces="application/json", consumes="application/json")
@CrossOrigin(origins = "*")
public class UserBooksController {

    private Logger logger = LoggerFactory.getLogger(UserBooksController.class);

    @Autowired
    private UserBooksService service;

    @ApiOperation(value = "Adicionar um livro na estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
    })
    @PostMapping
    public UserBooksTO post(@RequestBody UserBooksTO dto) {
        logger.info("Usuario solicitou adicionar um livro na estante " + dto);
        UserBooksTO userbook = service.save(dto);
        logger.info("Livro adicionado na estante " + userbook);
        return userbook;
    }

    @ApiOperation(value = "Retorna todos os livros da estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna os livros da estante do usuário"),
    })
    @GetMapping("/profile/{profileId}")
    public BookCaseTO getAllByProfile(@PathVariable int profileId) {
        logger.info("Usuario solicitou sua estante virutal" + profileId);
        BookCaseTO estante = service.getByProfileId(profileId);
        logger.info("Estante encontrada. " + estante);
        return estante;
    }

    @ApiOperation(value = "Editar um livro na estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro editado"),
    })
    @PutMapping()
    public UserBooksTO putUserBook(@RequestBody UserBooksTO dto){
        logger.info("Solicitacao para alterar um livro da estante " + dto);
        return service.update(dto);
    }

    @ApiOperation(value = "Inserir status em um livro na estante virtual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do livro alterado"),
            @ApiResponse(code = 404, message = "Livro não encontrado na estante"),
            @ApiResponse(code = 409, message = "Status inválido, livro não alterado")
    })
    @PutMapping("/status")
    public UserBooksTO putStatus(@RequestBody UserBookUpdateStatusTO dto) {
        logger.info("Solicitacaoo para alterar o status de um livro da estante " + dto);
        return service.updateStatus(dto);
    }

    @ApiOperation(value = "Deletar uma estante virtual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro deletado da estante")
    })
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        logger.info("Deletar estante" + id);
        service.deleteById(id);
    }
}
