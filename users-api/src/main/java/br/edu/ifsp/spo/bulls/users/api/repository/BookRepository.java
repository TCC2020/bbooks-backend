package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.dto.BookTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    HashSet<Book> findAll();

    boolean existsByIsbn10(String isbn10);

    @Query(value =
            "select new br.edu.ifsp.spo.bulls.users.api.dto.BookTO(" +
                    "b.id, b.isbn10, b.title, b.numberPage, b.language, b.publisher, b.publishedDate ,b.description, b.image) " +
                    "from Book b " +
                    "WHERE LOWER(b.title) like %:searchTerm% " +
                    "OR LOWER(b.isbn10) like %:searchTerm%"
    )
    Page<BookTO> search(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
}
