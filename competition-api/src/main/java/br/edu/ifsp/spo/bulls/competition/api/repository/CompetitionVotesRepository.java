package br.edu.ifsp.spo.bulls.competition.api.repository;

import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionVotes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CompetitionVotesRepository extends CrudRepository<CompetitionVotes, UUID> {

     boolean existsByProfileIdAndMember(int profileId, CompetitionMember member);

     List<CompetitionVotes> findByMember(CompetitionMember member);

    CompetitionVotes getVoteByMemberAndProfileId(CompetitionMember member, int profileId);
}
