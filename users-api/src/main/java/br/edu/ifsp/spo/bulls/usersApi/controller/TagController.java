package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagController {
    @Autowired
    private TagService service;

    @PostMapping
    public Tag save(Tag tag) {
        return service.save(tag);
    }

    @GetMapping("/profile/{profileId}")
    public LinkedHashSet<Tag> getByProfile(@PathVariable int profileId) {
        return service.getByProfile(profileId);
    }

    @PutMapping("/{tagId}/book/{userBookId}")
    public Tag putTagOnBook(Long tagId, Long userBookId) {
        return service.tagBook(tagId, userBookId);
    }

    @DeleteMapping("/{tagId}/book/{userBookId}")
    public HttpStatus delete(Long tagId, Long userBookId) {
        return service.untagBook(tagId, userBookId);
    }
}
