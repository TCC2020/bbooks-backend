package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository  extends CrudRepository<Review, UUID> {

    List<Review> findAllByBookOrderByCreationDate (Book book);
    List<Review> findAllByIdGoogleBookOrderByCreationDate (String idGoogleBook);

}
