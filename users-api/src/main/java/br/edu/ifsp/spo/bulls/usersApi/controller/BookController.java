package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    BookService service;

    @PostMapping
    public BookTO save(@RequestBody BookTO bookTO){
        return service.save(bookTO);
    }

    @GetMapping
    public HashSet<BookTO> get(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookTO getOne(@PathVariable int id){
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
    
    @PutMapping("/{id}")
    public BookTO update(@PathVariable int id, @RequestBody BookTO bookTo){
        return service.update(bookTo);
    }
}
