package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;

@RestController
@RequestMapping(value = "/books", produces="application/json", consumes="application/json")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    BookService service;

    @ApiOperation(value = "Cadastrar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o livro cadastrado"),
            @ApiResponse(code = 409, message = "Conflito ao cadastrar o livro. ISBN já está sendo utilizado"),
            @ApiResponse(code = 400, message = "O livro deve ter pelo menos 1 autor"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public BookTO save(@RequestBody BookTO bookTO){
        return service.save(bookTO);
    }

    @ApiOperation(value = "Listar livros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de livros")
    })
    @GetMapping
    public HashSet<BookTO> get(){
        return service.getAll();
    }


    @ApiOperation(value = "Buscar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o livro buscado"),
            @ApiResponse(code = 404, message = "Livro não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public BookTO getOne(@PathVariable int id){
        return service.getOne(id);
    }

    @ApiOperation(value = "Deletar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro deletado"),
            @ApiResponse(code = 404, message = "Livro não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @ApiOperation(value = "Alterar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o livro alterado"),
            @ApiResponse(code = 404, message = "Livro não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
            @ApiResponse(code = 400, message = "O livro deve ter pelo menos 1 autor")
    })
    @PutMapping("/{id}")
    public BookTO update(@PathVariable int id, @RequestBody BookTO bookTo){
        return service.update(bookTo);
    }
}
