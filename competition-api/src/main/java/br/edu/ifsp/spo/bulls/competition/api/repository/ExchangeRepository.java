package br.edu.ifsp.spo.bulls.competition.api.repository;

import br.edu.ifsp.spo.bulls.competition.api.domain.Exchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends CrudRepository<Exchange, UUID> {
    List<Exchange> findByReceiverId(UUID receiverId);

    List<Exchange> findByRequesterId(UUID requesterId);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM BOOK_EXCHANGES_RECEIVER_ADS e1, BOOK_EXCHANGES_REQUESTER_ADS e2 WHERE e1.RECEIVER_ADS_ID = :id OR e2.REQUESTER_ADS_ID  = :id ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END", nativeQuery = true)
    Boolean hasExchanges(@Param("id") UUID id);

    Optional<Exchange> findByToken(UUID exchangeToken);
}
