package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.TagRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import javafx.scene.effect.SepiaTone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {
    @Autowired
    private TagRepository repository;
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private UserBooksService userBooksService;

    public Tag save(Tag tag) {
        return repository.save(tag);
    }

    public List<Tag> getByProfile(int profileId) {
        return repository.findByProfileId(profileId);
    }

    public Tag tagBook(Long tagId, Long userBookId) {
        Tag tag = repository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found."));
        tag.getBooks().add(userBooksRepository.findById(userBookId).orElseThrow(() -> new ResourceNotFoundException("Tag not found.")));
        return repository.save(tag);
    }

    public HttpStatus untagBook(Long tagId, Long userBookId) {
        Tag tag = repository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag not found."));
        tag.getBooks().remove(userBooksRepository.findById(userBookId).orElseThrow(() -> new ResourceNotFoundException("Tag not found.")));
        repository.save(tag);
        return HttpStatus.ACCEPTED;
    }

    public Tag getbyId(Long idTag) {
        return repository.findById(idTag).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }

    public List<Tag> getByIdBook(Long idUserBook) {
        List<Tag> tags = new ArrayList<>();

        for(Tag tag: repository.findAll()) {
            for(UserBooks b: tag.getBooks()){
                if(b.getId() == idUserBook)
                    tags.add(tag);
            }
        }
        return tags;
    }
}

