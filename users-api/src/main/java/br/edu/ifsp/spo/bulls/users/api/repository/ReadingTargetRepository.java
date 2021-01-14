package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTarget;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReadingTargetRepository extends CrudRepository<ReadingTarget, UUID> {

    Optional<ReadingTarget> findByProfileIdAndYear(int profileId, Integer year);

    List<ReadingTarget> findByProfileId(int profileId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM READING_TARGETS_TARGETS WHERE TARGETS_ID = :id", nativeQuery = true)
    void deleteTargetRelationship(@Param("id") Long id);
}
