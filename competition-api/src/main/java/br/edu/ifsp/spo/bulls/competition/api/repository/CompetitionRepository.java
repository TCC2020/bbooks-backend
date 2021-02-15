package br.edu.ifsp.spo.bulls.competition.api.repository;

import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends CrudRepository<Competition, UUID> {
}