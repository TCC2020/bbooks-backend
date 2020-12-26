package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.Review;
import br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ReviewRepository  extends CrudRepository<Review, UUID> {

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO(" +
                    "b.id, b.title, b.body, b.idGoogleBook, b.profile.id, b.creationDate) " +
                    "FROM Review b " +
                    "WHERE b.idGoogleBook = :idGoogleBook " +
                    "ORDER BY b.creationDate DESC"
    )
    Page<ReviewTO> searchByGoogleBook(
            @Param("idGoogleBook") String idGoogleBook,
            Pageable pageable);

    @Query(value =
            "select new br.edu.ifsp.spo.bulls.users.api.dto.ReviewTO(" +
                    "r.id, r.title, r.body, r.book.id, r.idGoogleBook, r.profile.id, r.creationDate) " +
                    "FROM Review r " +
                    "WHERE r.book.id = :bookId " +
                    "ORDER BY r.creationDate DESC"
    )
    Page<ReviewTO> searchByBookId(
            @Param("bookId") int bookId,
            Pageable pageable);

}
