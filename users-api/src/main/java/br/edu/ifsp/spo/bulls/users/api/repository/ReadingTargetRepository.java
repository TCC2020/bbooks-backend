package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReadingTargetRepository extends CrudRepository<ReadingTarget, UUID> {

    Optional<ReadingTarget> findByProfileIdAndYear(int profileId, Integer year);

    List<ReadingTarget> findByProfileId(Long profileId);
}
