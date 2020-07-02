package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository repository;

    public Author save (Author author){
        author.setName(author.getName().toLowerCase());
        return repository.save(author);
    }

    public Author verifyIfAuthorExists(Author author){

        if(!repository.existsByName(author.getName().toLowerCase())){
            return this.save(author);
        }else{
            return repository.findByName(author.getName().toLowerCase());
        }
    }
}
