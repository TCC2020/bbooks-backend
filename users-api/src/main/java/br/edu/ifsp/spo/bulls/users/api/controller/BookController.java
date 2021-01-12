package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.users.api.dto.BookSearchTO;
import br.edu.ifsp.spo.bulls.users.api.dto.BookTO;
import br.edu.ifsp.spo.bulls.users.api.service.BookService;
import br.edu.ifsp.spo.bulls.users.api.webclient.Client;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;

@RestController
@RequestMapping(value = "/books", produces="application/json")
@CrossOrigin(origins = "*")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService service;

    @Autowired
    private Client client;

    @ApiOperation(value = "Cadastrar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o livro cadastrado"),
            @ApiResponse(code = 409, message = "Conflito ao cadastrar o livro. ISBN já está sendo utilizado"),
            @ApiResponse(code = 400, message = "O livro deve ter pelo menos 1 autor"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public BookTO save(@RequestBody BookTO bookTO){
        logger.info("Cadastrando novo Livro " + bookTO);
        return service.save(bookTO);
    }

    @ApiOperation(value = "Listar livros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de livros")
    })
    @GetMapping
    public HashSet<BookTO> get(){
        logger.info("Requisitando todos os livros do cadastro");
        HashSet<BookTO> livros = service.getAll();
        logger.info("Livros retornados: " + livros);
        return livros;
    }

    @ApiOperation(value = "Buscar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o livro buscado"),
            @ApiResponse(code = 404, message = "Livro não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/{id}")
    public BookTO getOne(@PathVariable int id){
        logger.info("Requisitando informações do livro com id: " + id);
        BookTO livro = service.getOne(id);
        logger.info("Livro encontrado: " + livro);
        return livro;
    }

    @ApiOperation(value = "Deletar um livro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Livro deletado"),
            @ApiResponse(code = 404, message = "Livro não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        logger.info("Requisião solicitada para deletar o livro " + id);
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
        logger.info("Requisicao para atualizar o cadastro do livro" + bookTo);
        return service.update(bookTo);
    }

    @GetMapping("/search")
    public Page<BookTO> search(@RequestParam String search, @RequestParam Integer page, @RequestParam Integer size){
        logger.info("Requisitando lista de  livros com titulo ou isbn: " + search);
        Page<BookTO> livros = service.search(search, page, size);
        logger.info("Livros encontrados: " + livros);
        return livros;
    }

    @PostMapping("/searchByString")
    public BookSearchTO searchByString(@RequestBody BookSearchTO bookSearchTO, @RequestParam Integer size){
        logger.info("Requisitando lista de  livros com titulo ou isbn: " + bookSearchTO.getSearch());
        return client.searchBooks(bookSearchTO, size);
    }


}
