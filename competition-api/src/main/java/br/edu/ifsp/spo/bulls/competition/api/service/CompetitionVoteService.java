package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesSaveTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompetitionVoteService {
    @Autowired
    private CompetitionVotesRepository repository;

    @Autowired
    private CompetitionMemberRepository memberRepository;

    @Autowired
    private UserCommonFeign feign;

    public void deleteVote(UUID voteId, String tokenValue) {
        // TODO: Verificar se o member existe
        // TODO: Verificar de o token existe
    }

    public List<CompetitionVotesSaveTO> getVotesByMember(UUID memberId) {
        // TODO: Verificar se o member existe e está em status "aceito"

        return null;
    }

    public CompetitionVotesSaveTO vote(CompetitionVotesSaveTO vote) {
        // TODO: Verificar se o member existe e está em status "aceito"

        return null;
    }

    public CompetitionVotesSaveTO updateVote(CompetitionVotesSaveTO vote, UUID voteId, String tokenValue) {
        // TODO: Verificar de o token existe
        // TODO: Verificar se o member existe e está em status "aceito"

        return null;
    }
}
