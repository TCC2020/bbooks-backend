package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.common.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.bean.BookBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Author;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.dto.BookSearchTO;
import br.edu.ifsp.spo.bulls.users.api.dto.BookTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.feign.GoogleAPiFeign;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookBeanUtil beanUtil;

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GoogleAPiFeign googleFeign;

    public BookTO save(BookTO entity) {

        verificaSeIsbnEstsCadastrado(entity);
        verificaSeTemAutores(entity);

        List<Author> author = new ArrayList<>();

        for(int i = 0; i<entity.getAuthors().size(); i++){
            author.add(authorService.returnTheAuthorFromDb(entity.getAuthors().get(i)));
        }

        Book book = beanUtil.toBook(entity);
        book.setAuthors(author);

        Book result = repository.save(book);

        return beanUtil.toBookTO(result);
    }

    private void verificaSeIsbnEstsCadastrado(BookTO entity) {
        if(repository.existsByIsbn10(entity.getIsbn10())){
            throw new ResourceConflictException(CodeException.BK001.getText(), CodeException.BK001);
        }
    }

    private void verificaSeTemAutores(BookTO entity) {
        if(entity.getAuthors() == null){
            throw new ResourceBadRequestException(CodeException.BK003.getText(), CodeException.BK003);
        }
    }

    public HashSet<BookTO> getAll(){
        HashSet<Book> books = repository.findAll();

        return beanUtil.toBookTO(books);
    }

    public BookTO getOne(int id){
        Book book = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));
        return beanUtil.toBookTO(book);
    }

    public void delete(int id){
        repository.findById(id).orElseThrow( () -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002) );
        repository.deleteById(id);
    }

    public BookTO update(BookTO bookTo){

        this.verificaSeTemAutores(bookTo);
        Book retorno = repository.findById(bookTo.getId()).map(book -> {
            book.setAuthors(bookTo.getAuthors());
            book.setDescription(bookTo.getDescription());
            book.setIsbn10(bookTo.getIsbn10());
            book.setLanguage(bookTo.getLanguage());
            book.setNumberPage(bookTo.getNumberPage());
            book.setPublishedDate(bookTo.getPublishedDate());
            book.setPublisher(bookTo.getPublisher());
            book.setTitle(bookTo.getTitle());
            return repository.save(book);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));


        return beanUtil.toBookTO(retorno);
    }

    public Page<BookTO> search(String search, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return repository.search(search.toLowerCase(), pageRequest);
    }

    public HttpStatus updateBookImage(String url, int id) {
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        book.setImage(url);
        repository.save(book);
        return HttpStatus.CREATED;
    }

    public BookSearchTO searchBooks(BookSearchTO search, Integer size) {
        BookSearchTO bookSearchTO = new BookSearchTO();

        Page<BookTO> books = search(search.getSearch(), search.getPage().intValue(), size/2);
        LinkedHashMap googleBooks = googleFeign.getBooks((int) (size - books.stream().count()), search.getSearch(), search.getPage().intValue());

        bookSearchTO.setSearch(search.getSearch());
        bookSearchTO.setGoogleBooks(googleBooks);
        bookSearchTO.setPage(search.getPage());
        bookSearchTO.setBooks(books);

        return bookSearchTO;
    }
}
