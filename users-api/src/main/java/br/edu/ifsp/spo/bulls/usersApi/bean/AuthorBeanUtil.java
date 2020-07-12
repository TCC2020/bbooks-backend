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

    public List<AuthorTO> toAuthorToList(List<Author> authors) {

        List<AuthorTO> authorsTo = new ArrayList<AuthorTO>();

        for (Author author: authors ) {
            authorsTo.add(this.toAuthorTo(author));
        }

        return authorsTo;
    }

    public AuthorTO toAuthorTo(Author author) {
        AuthorTO authorTO = new AuthorTO();
        System.out.println(author);
        System.out.println(authorTO);
        try{
            BeanUtils.copyProperties(author, authorTO);
        }catch(Exception e) {

        }
        System.out.println(authorTO);
        return authorTO;
    }

}
