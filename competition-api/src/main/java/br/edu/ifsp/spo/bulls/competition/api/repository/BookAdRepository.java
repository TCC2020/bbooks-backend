package br.edu.ifsp.spo.bulls.competition.api.repository;

import br.edu.ifsp.spo.bulls.competition.api.domain.BookAd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookAdRepository extends CrudRepository<BookAd, UUID> {
    List<BookAd> findAll();

    @Query(value = "SELECT * FROM book_ads WHERE user_id = :id", nativeQuery = true)
    List<BookAd> findByUserId(@Param("id") UUID id);
}
