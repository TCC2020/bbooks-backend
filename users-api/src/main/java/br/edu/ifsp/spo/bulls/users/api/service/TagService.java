package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.TagRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository repository;
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public Tag save(Tag tag) {
        return repository.save(tag);
    }

    public List<Tag> getByProfile(int profileId) {

        Profile profile = profileRepository.findById(profileId).orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
        return repository.findByProfile(profile);
    }

    public Tag tagBook(Long tagId, Long userBookId) {
        System.out.println(userBookId);
        Tag tag = repository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        System.out.println(tag);
        tag.getBooks().add(userBooksRepository.findById(userBookId).orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001)));
        return repository.save(tag);
    }

    public HttpStatus untagBook(Long tagId, Long userBookId) {
        Tag tag = repository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        tag.getBooks().remove(userBooksRepository.findById(userBookId).orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001)));
        repository.save(tag);
        return HttpStatus.ACCEPTED;
    }

    public void delete(Long tagId){
        Tag tag = repository.findById(tagId).orElseThrow( () -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
        repository.delete(tag);
    }

    public Tag update(Long idTag, Tag tagBody){
        if(!tagBody.getId().equals(idTag))
            throw new ResourceBadRequestException(CodeException.TG002.getText(), CodeException.TG002);

        return repository.findById(idTag).map( tag -> {
            tag.setName(tagBody.getName());
            tag.setColor(tagBody.getColor());
            return repository.save(tag);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));

    }

    public Tag getbyId(Long idTag) {
        return repository.findById(idTag).orElseThrow(() -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
    }

    public List<Tag> getByIdBook(Long idUserBook) {
        List<Tag> tags = new ArrayList<>();

        for(Tag tag: repository.findAll()) {
            for(UserBooks b: tag.getBooks()){
                if(b.getId().equals(idUserBook))
                    tags.add(tag);
            }
        }
        return tags;
    }
}

