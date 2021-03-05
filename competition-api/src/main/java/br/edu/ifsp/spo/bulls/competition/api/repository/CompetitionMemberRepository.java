package br.edu.ifsp.spo.bulls.competition.api.repository;

import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetitionMemberRepository extends CrudRepository<CompetitionMember, UUID> {

    @Query(
        "SELECT m " +
        "FROM CompetitionMember m " +
        "WHERE m.competition.id = :competitionId " +
            "AND m.role = :role "
    )
    CompetitionMember getCreatorOfCompetition(@Param("competitionId") UUID competitionId, @Param("role")Role role);

    void deleteAllByCompetition(Competition competition);

    Optional<CompetitionMember> getByProfileIdAndCompetition(int profileId, Competition competition);

    @Query(value =
            "SELECT c "+
                    "FROM CompetitionMember m " +
                    "INNER JOIN m.competition  c " +
                    "WHERE m.profileId = :profileId"
    )
    Page<Competition> getByProfile(@Param("profileId") int profileId,Pageable pageable);

    @Query(
            "SELECT m " +
                    "FROM CompetitionMember m " +
                    "WHERE m.competition.id = :competitionId "
    )
    Page<CompetitionMember> getMemberOfCompetition(@Param("competitionId") UUID competitionId, Pageable pageable);

    List<CompetitionMember> getByCompetitionAndRoleAnsStatus(Competition competition, Role role, Status status);
}
