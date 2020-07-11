package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    HashSet<Book> findAll();

    Boolean existsByIsbn(String isbn);
}
