package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.TagRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import com.google.rpc.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Tag tag = repository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
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
        this.retiraTagDeLivros(tag);
        repository.delete(tag);
    }

    public Tag update(Long idTag, Tag tagBody){
        if(tagBody.getId() != idTag)
            throw new ResourceBadRequestException(CodeException.TG002.getText(), CodeException.TG002);

        Tag resultado = repository.findById(idTag).map( tag -> {
            tag.setName(tagBody.getName());
            tag.setColor(tagBody.getColor());
            return repository.save(tag);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));

        return resultado;
    }

    public Tag getbyId(Long idTag) {
        return repository.findById(idTag).orElseThrow(() -> new ResourceNotFoundException(CodeException.TG001.getText(), CodeException.TG001));
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

    private void retiraTagDeLivros(Tag tag){
        for(UserBooks userBook : tag.getBooks()){
            this.untagBook(tag.getId(), userBook.getId() );
        }
    };
}

