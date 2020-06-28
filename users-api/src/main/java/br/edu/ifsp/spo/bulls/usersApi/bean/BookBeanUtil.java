package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.ProfileTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class BookBeanUtil {

    public Book toBook(BookTO bookTo) {
        Book book = new Book();

        try{
            BeanUtils.copyProperties(bookTo, book);
        }catch(Exception e) {

        }

        return book;
    }

    public HashSet<BookTO> toBookTO(HashSet<Book> books) {

        HashSet<BookTO> booksTO = new HashSet<BookTO>();

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

        }

        return bookTo;
    }
}
