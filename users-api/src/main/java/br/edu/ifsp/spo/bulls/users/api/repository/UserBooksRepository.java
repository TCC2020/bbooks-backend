package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksDataStatusTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserBooksRepository  extends CrudRepository<UserBooks, Long> {
    Set<UserBooks> findByProfile(Profile profile);

    @Query(value = "SELECT * FROM user_books WHERE profile_id =:profileId AND finish_date is not null ORDER BY finish_date ASC", nativeQuery = true)
    Set<UserBooks> findByProfileOrderByFinishDateDesc(@Param("profileId") int profileId);

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.users.api.dto.UserBooksDataStatusTO( r.idBookGoogle, " +
                    "count(r.status) filter (where r.status = 'QUERO_LER'), " +
                    "count(r.status) filter (where r.status = 'LENDO'), " +
                    "count(r.status) filter (where r.status = 'LIDO'), " +
                    "count(r.status) filter (where r.status = 'EMPRESTADO'), " +
                    "count(r.status) filter (where r.status = 'RELENDO'), " +
                    "count(r.status) filter (where r.status = 'INTERROMPIDO')) " +
                    "FROM UserBooks r " +
                    "WHERE r.idBookGoogle = :googleBook "+
                    "GROUP BY r.status, r.idBookGoogle")
    // Exemplo: select  from user_books r where id_book_google = 'zGdwDwAAQBAJ' GROUP BY Status;
    List<?> findInfoGoogleBook(@Param("googleBook") String googleBook);

    @Query(value =
            "SELECT r.status, count(r.status), r.book.id from UserBooks r " +
                    "WHERE r.book.id =: bookId "+
                    "GROUP BY status, r.book.id")
        // Exemplo: select  from user_books r where id_book_google = 'zGdwDwAAQBAJ' GROUP BY Status;
    UserBooksDataStatusTO findInfoBook(@Param("bookId") String bookid);
}
