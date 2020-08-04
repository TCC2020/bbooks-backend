package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.TagRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class TagService {
    @Autowired
    private TagRepository repository;
    @Autowired
    private UserBooksRepository userBooksRepository;

    public Tag save(Tag tag) {
        return repository.save(tag);
    }

    public LinkedHashSet<Tag> getByProfile(int profileId) {
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
}
