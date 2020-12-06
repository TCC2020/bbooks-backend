package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository repository;


    public Author save (Author author){
        this.verifyIfAuthorExist(author);

        author.setName(author.getName().toLowerCase());

        return repository.save(author);
    }

    public Author getOne (int id){
        Author author = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.AU001.getText(), CodeException.AU001));
        return repository.save(author);
    }

    public Author getByName (String name){
        Author author = repository.findByName(name.toLowerCase()).orElseThrow( () -> new ResourceNotFoundException(CodeException.AU001.getText(), CodeException.AU001));
        return repository.save(author);
    }

    public List<Author> getAll(){
        return repository.findAll();
    }

    public void delete(int id){

        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException(CodeException.AU001.getText(), CodeException.AU001);
        }

    }

    public Author update(Author author, int id){

        verifyIfAuthorExist(author);

        return repository.findById(id).map(author1 -> {
            author1.setName(author.getName());
            return repository.save(author1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.AU001.getText(), CodeException.AU001));
    }

    public Author returnTheAuthorFromDb(Author author2){

        if(!repository.existsByName(author2.getName().toLowerCase())){
            return this.save(author2);
        }else{
            return repository.findByName(author2.getName().toLowerCase()).get();
        }
    }

    public void verifyIfAuthorExist(Author author2){

        if(repository.existsByName(author2.getName().toLowerCase())){
            throw new ResourceConflictException(CodeException.AU002.getText(), CodeException.AU002);
        }
    }
}
