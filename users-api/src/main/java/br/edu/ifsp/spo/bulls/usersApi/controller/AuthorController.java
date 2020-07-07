package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import br.edu.ifsp.spo.bulls.usersApi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

    @Autowired
    AuthorService service;

    @PostMapping
    public AuthorTO save(@RequestBody AuthorTO author){
        return service.save(author);
    }

    @GetMapping
    public List<AuthorTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AuthorTO getOne(@PathVariable int id){
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public AuthorTO update(@RequestBody AuthorTO author, @PathVariable int id){
        return service.update(author, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @GetMapping("/name/{name}")
    public AuthorTO getByName(@PathVariable String name){
        return service.getByName(name);
    }

}
