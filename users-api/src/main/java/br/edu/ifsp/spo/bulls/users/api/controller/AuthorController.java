package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.domain.Author;
import br.edu.ifsp.spo.bulls.users.api.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/authors", produces="application/json")
@CrossOrigin(origins = "*")
public class AuthorController {

    private Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService service;

    @ApiOperation(value = "Cadastrar um autor")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o autor cadastrado"),
            @ApiResponse(code = 409, message = "Conflito ao cadastrar o autor. Nome já deve estar cadastro"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Author save(@RequestBody Author author){
        logger.info("Cadastrando novo autor " + author);
        return service.save(author);
    }

    @ApiOperation(value = "Retorna informações de todos os autores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de autores")
    })
    @GetMapping
    public List<Author> getAll(){
        logger.info("Requisitando todos os autores do cadastro");
        List<Author> authors = service.getAll();
        logger.info("Autores retornados: " + authors);
        return authors;
    }

    @ApiOperation(value = "Retorna um profile a partir do identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o profile"),
            @ApiResponse(code = 404, message = "Profile não encontrado"),
    })
    @GetMapping("/{id}")
    public Author getOne(@PathVariable int id){
        logger.info("Requisitando informações de autor com id: " + id);
        Author author = service.getOne(id);
        logger.info("Autor encontrado: " + author);
        return author;
    }

    @ApiOperation(value = "Alterar um autor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o autor alterado"),
            @ApiResponse(code = 409, message = "Conflito ao alterar o autor. Nome já deve estar cadastro"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping(value = "/{id}", consumes="application/json")
    public Author update(@RequestBody Author author, @PathVariable int id){
        logger.info("Requisicao para atualizar o cadastro do autor " + author);
        return service.update(author, id);
    }

    @ApiOperation(value = "Deleta o profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profile deletado"),
            @ApiResponse(code = 404, message = "Profile não encontrado"),
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @ApiOperation(value = "Retorna um profile a partir do identificador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o profile"),
            @ApiResponse(code = 404, message = "Profile não encontrado"),
    })
    @GetMapping("/name/{name}")
    public Author getByName(@PathVariable String name){
        return service.getByName(name);
    }

}
