package br.edu.ifsp.spo.bulls.competition.api.repository;

import br.edu.ifsp.spo.bulls.competition.api.domain.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends CrudRepository<Exchange, UUID> {
    List<Exchange> findByReceiverId(UUID id);
}
