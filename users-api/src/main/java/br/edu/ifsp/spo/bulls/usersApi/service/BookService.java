package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.BookBeanUtil;
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

    public BookTO save(BookTO entity) {
        Book book = beanUtil.toBook(entity);
        
        Book result = repository.save(book);

        return beanUtil.toBookTO(result);
    }
}
