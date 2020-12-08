package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.bean.BookBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.Author;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.dto.BookTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
}
