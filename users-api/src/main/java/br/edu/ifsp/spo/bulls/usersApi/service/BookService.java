package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.BookBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookBeanUtil beanUtil;

    @Autowired
    BookRepository repository;

    @Autowired
    AuthorService authorService;

    public BookTO save(BookTO entity) {
        Book book = beanUtil.toBook(entity);
        System.out.println(entity.toString());
        System.out.println(book.toString());

//        for(int i = 0; i<book.getAuthors().size(); i++){
//            book.setAuthors(authorService.verifyIfAuthorExists(book.getAuthors().get(i)));
//        }

        Book result = repository.save(book);

        return beanUtil.toBookTO(result);
    }
}
