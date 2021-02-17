package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionVotesTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompetitionVoteService {
    public void deleteVote(UUID voteId, String tokenValue) {
        // TODO: Verificar se o member existe
        // TODO: Verificar de o token existe
    }

    public List<CompetitionVotesTO> getVotesByMember(UUID memberId) {
        // TODO: Verificar se o member existe e está em status "aceito"

        return null;
    }

    public CompetitionVotesTO vote(CompetitionVotesTO vote) {
        // TODO: Verificar se o member existe e está em status "aceito"

        return null;
    }

    public CompetitionVotesTO updateVote(CompetitionVotesTO vote, UUID voteId, String tokenValue) {
        // TODO: Verificar de o token existe
        // TODO: Verificar se o member existe e está em status "aceito"

        return null;
    }
}
