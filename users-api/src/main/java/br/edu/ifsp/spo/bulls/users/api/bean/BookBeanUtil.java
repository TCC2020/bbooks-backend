package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.dto.BookTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.HashSet;

@Component
public class BookBeanUtil {

    private Logger logger = LoggerFactory.getLogger(BookBeanUtil.class);

    public Book toBook(BookTO bookTo) {
        Book book = new Book();

        try{
            BeanUtils.copyProperties(bookTo, book);
        }catch(Exception e) {
            logger.error("Error while converting BookTO to Book: " + e);
        }

        return book;
    }

    public HashSet<BookTO> toBookTO(HashSet<Book> books) {

        HashSet<BookTO> booksTO = new HashSet<>();

        for (Book book: books ) {
            booksTO.add(this.toBookTO(book));
        }

        return booksTO;
    }

    public BookTO toBookTO(Book book) {
        BookTO bookTo = new BookTO();

        try{
            BeanUtils.copyProperties(book, bookTo);
        }catch(Exception e) {
            logger.error("Error while converting Book to BookTO: " +  e);
        }

        return bookTo;
    }
}
