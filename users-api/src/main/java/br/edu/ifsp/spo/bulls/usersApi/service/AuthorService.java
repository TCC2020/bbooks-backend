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
        System.out.println("TO " + authorTo.toString());
        Author author = beanUtil.toAuthor(authorTo);
        System.out.println("1 " + author.toString());
        author.setName(author.getName().toLowerCase());
        System.out.println("2 " + author.toString());
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
