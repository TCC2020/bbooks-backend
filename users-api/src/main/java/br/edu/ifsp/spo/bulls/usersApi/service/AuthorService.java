package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.AuthorBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
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

    @Autowired
    AuthorBeanUtil beanUtil;

    public AuthorTO save (AuthorTO authorTo){
        this.verifyIfAuthorExist(authorTo);

        Author author = beanUtil.toAuthor(authorTo);
        author.setName(author.getName().toLowerCase());

        return beanUtil.toAuthorTo(repository.save(author));
    }

    public AuthorTO getOne (int id){
        Author author = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Author not found"));
        return beanUtil.toAuthorTo(repository.save(author));
    }

    public AuthorTO getByName (String name){
        Author author = repository.findByName(name.toLowerCase()).orElseThrow( () -> new ResourceNotFoundException("Author not found"));
        return beanUtil.toAuthorTo(repository.save(author));
    }

    public List<AuthorTO> getAll(){
        List<Author> authors = repository.findAll();

        return beanUtil.toAuthorToList(authors);
    }

    public void delete(int id){

        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new  ResourceNotFoundException("Author not found");
        }

    }

    public AuthorTO update(AuthorTO authorTO, int id){

        verifyIfAuthorExist(authorTO);
        Author retorno = repository.findById(id).map(author -> {
            author.setName(authorTO.getName());
            return repository.save(author);
        }).orElseThrow( () -> new ResourceNotFoundException("Author not found"));

        return beanUtil.toAuthorTo(retorno);
    }

    public Author returnTheAuthorFromDb(Author author2){

        if(!repository.existsByName(author2.getName().toLowerCase())){
            AuthorTO authorTO  = this.save(beanUtil.toAuthorTo(author2));
            return beanUtil.toAuthor(authorTO);
        }else{
            return repository.findByName(author2.getName().toLowerCase()).get();
        }
    }

    public void verifyIfAuthorExist(AuthorTO author2){

        if(repository.existsByName(author2.getName().toLowerCase())){
            throw new ResourceConflictException("Autor ja existe");
        }
    }
}
