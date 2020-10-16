package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.AuthorTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorBeanUtil {

    private Logger logger = LoggerFactory.getLogger(AuthorBeanUtil.class);

    public Author toAuthor(AuthorTO authorTO) {
        Author author = new Author();

        try{
            BeanUtils.copyProperties(authorTO, author);
        }catch(Exception e) {
            logger.error("Error while converting AuthorTO to Author: " +  e);
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
        try{
            BeanUtils.copyProperties(author, authorTO);
        }catch(Exception e) {
            logger.error("Error while converting Author to AuthorTO: " + e);
        }
        System.out.println(authorTO);
        return authorTO;
    }

}
