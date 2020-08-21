package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagController {
    @Autowired
    private TagService service;

    @PostMapping
    public Tag save(@RequestBody Tag tag) {
        return service.save(tag);
    }

    @GetMapping("/profile/{profileId}")
    public List<Tag> getByProfile(@PathVariable int profileId) {
        return service.getByProfile(profileId);
    }
    @GetMapping("/book/{idUserBook}")
    public List<Tag> getByBook(@PathVariable Long idUserBook) {
        return service.getByIdBook(idUserBook);
    }
    @GetMapping("/{idTag}")
    public Tag getById(@PathVariable Long idTag) {
        return service.getbyId(idTag);
    }

    @PutMapping("/{tagId}/book/{userBookId}")
    public Tag putTagOnBook(@PathVariable Long tagId,@PathVariable Long userBookId) {
        return service.tagBook(tagId, userBookId);
    }
    @DeleteMapping("/{tagId}/book/{userBookId}")
    public HttpStatus untag(@PathVariable Long tagId, @PathVariable Long userBookId) {
        return service.untagBook(tagId, userBookId);
    }

    @DeleteMapping("/{tagId}")
    public void delete(@PathVariable Long tagId) {
        service.delete(tagId);
    }
}
