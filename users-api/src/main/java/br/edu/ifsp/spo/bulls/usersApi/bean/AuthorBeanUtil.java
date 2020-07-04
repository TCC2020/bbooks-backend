package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorBeanUtil {

    public Author toAuthor(AuthorTO authorTO) {
        Author author = new Author();

        try{
            BeanUtils.copyProperties(authorTO, author);
        }catch(Exception e) {

        }

        return author;
    }

    public List<AuthorTO> toAuthorTo(List<Author> authors) {

        List<AuthorTO> authorsTo = new ArrayList<AuthorTO>();

        for (Author author: authors ) {
            authorsTo.add(this.toAuthorTo(author));
        }

        return authorsTo;
    }

    public List<Author> toAuthor(List<AuthorTO> authorsTo) {

        List<Author> authors  = new ArrayList<Author>();

        for (Author author: authors ) {
            authorsTo.add(this.toAuthorTo(author));
        }

        return authors;
    }

    public AuthorTO toAuthorTo(Author author) {
        AuthorTO authorTO = new AuthorTO();

        try{
            BeanUtils.copyProperties(author, authorTO);
        }catch(Exception e) {

        }

        return authorTO;
    }

}
