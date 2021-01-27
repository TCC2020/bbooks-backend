package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksDataStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.service.UserBooksService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookcases", produces="application/json")
@CrossOrigin(origins = "*")
public class UserBooksController {

    private Logger logger = LoggerFactory.getLogger(UserBooksController.class);

    @Autowired
    private UserBooksService service;

    @ApiOperation(value = "Adicionar um livro na estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro adicionado"),
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
    public BookCaseTO getAllByProfile(@PathVariable int profileId, @RequestParam(value = "timeLine", required=false) boolean timeLine) {
        logger.info("Usuario solicitou sua estante virutal" + profileId);
        BookCaseTO estante = service.getByProfileId(profileId,timeLine);
        logger.info("Estante encontrada. " + estante);
        return estante;
    }

    @ApiOperation(value = "Retorna informações de quantas pessoas querem ler/estão lendo (todos os status) o livro ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de informações por livro informado"),
            @ApiResponse(code = 404, message = "Livro informado não existe na base de dados")
    })
    @GetMapping("/status-data")
    public List<?>getDataStatusByBooks(@RequestParam(name = "googleBook", required=false) String googleBook,
                                                      @RequestParam(name = "bookId", required=false, defaultValue = "0") int bookId) {
        logger.info("Buscando informações de leitura do livro " + googleBook + " " + bookId);
        List<?> info = service.getStatusData(googleBook,bookId);
        logger.info("Informações encontradas. " + info);
        return info;
    }

    @ApiOperation(value = "Editar um livro na estante do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro editado"),
            @ApiResponse(code = 404, message = "Userbook não encontrado"),
            @ApiResponse(code = 409, message = "Status não pode estar vazio")
    })
    @PutMapping("/{id}")
    public UserBooksTO putUserBook(@RequestBody UserBooksTO dto, @PathVariable Long id){
        logger.info("Solicitacao para alterar um livro da estante " + dto);
        return service.update(dto, id);
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
