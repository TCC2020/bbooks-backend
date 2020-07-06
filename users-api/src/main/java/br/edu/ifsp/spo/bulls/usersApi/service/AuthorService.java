package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.AuthorBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository repository;

    @Autowired
    AuthorBeanUtil beanUtil;

    public AuthorTO save (AuthorTO authorTo){
        Author author = beanUtil.toAuthor(authorTo);
        author.setName(author.getName().toLowerCase());

        return beanUtil.toAuthorTo(repository.save(author));
    }

    public AuthorTO getOne (int id){
        Author author = repository.findById(id).orElseThrow();
        return beanUtil.toAuthorTo(repository.save(author));
    }

    public Author verifyIfAuthorExists(Author author2){

        if(!repository.existsByName(author2.getName().toLowerCase())){
            AuthorTO authorTO  = this.save(beanUtil.toAuthorTo(author2));
            return beanUtil.toAuthor(authorTO);
        }else{
            return repository.findByName(author2.getName().toLowerCase());
        }
    }
}
