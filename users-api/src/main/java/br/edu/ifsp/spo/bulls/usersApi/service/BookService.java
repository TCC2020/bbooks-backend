package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.BookBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookBeanUtil beanUtil;

    @Autowired
    BookRepository repository;

    @Autowired
    AuthorService authorService;

    public BookTO save(BookTO entity) {

        List<Author> author = new ArrayList<Author>();

        for(int i = 0; i<entity.getAuthors().size(); i++){
            author.add(authorService.verifyIfAuthorExists(entity.getAuthors().get(i)));
        }

        Book book = beanUtil.toBook(entity);
        book.setAuthors(author);

        Book result = repository.save(book);

        return beanUtil.toBookTO(result);
    }

    public HashSet<BookTO> getAll(){
        HashSet<Book> books = repository.findAll();

        return beanUtil.toBookTO(books);
    }
}
