package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.service.UserBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bookcases", produces="application/json", consumes="application/json")
@CrossOrigin(origins = "*")
public class UserBooksController {
    @Autowired
    private UserBooksService service;

    @PostMapping
    public UserBooksTO post(@RequestBody UserBooksTO dto) {
        return service.save(dto);
    }

    @GetMapping("/profile/{profileId}")
    public BookCaseTO getAllByProfile(@PathVariable int profileId) {
        return service.getByProfileId(profileId);
    }
    @PutMapping()
    public UserBooksTO putUserBook(@RequestBody UserBooksTO dto){
        return service.update(dto);
    }

    @PutMapping("/status")
    public UserBooksTO putStatus(@RequestBody UserBookUpdateStatusTO dto) {
        return service.updateStatus(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }
}
