package br.edu.ifsp.spo.bulls.users.api.webclient;

import br.edu.ifsp.spo.bulls.users.api.dto.BookSearchTO;
import br.edu.ifsp.spo.bulls.users.api.dto.BookTO;
import br.edu.ifsp.spo.bulls.users.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Service
public class Client {

    @Autowired
    private BookService bookService;


    private WebClient wc;

    public void setup(int maxResults, String search, int pageIndex) {
        this.wc =  WebClient.create("https://www.googleapis.com/books/v1/volumes?maxResults="
                + maxResults + "&q=" + search + "&startIndex=" + pageIndex);
    }

    public BookSearchTO searchBooks(BookSearchTO search) {
        Page<BookTO> books = bookService.search(search.getSearch(), search.getPage().intValue(), 20);
        setup((int) (40 - books.stream().count()), search.getSearch(), search.getPage().intValue());
        LinkedHashMap googleBooks = wc.get().exchange()
                .block()
                .bodyToMono(LinkedHashMap.class)
                .block();
        BookSearchTO bookSearchTO = new BookSearchTO();
        bookSearchTO.setSearch(search.getSearch());
        bookSearchTO.setGoogleBooks(googleBooks);
        bookSearchTO.setPage(search.getPage());
        bookSearchTO.setBooks(books.getContent());
        return bookSearchTO;
    }
}
