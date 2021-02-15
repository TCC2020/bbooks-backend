package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CompetitionService {
    public void delete(UUID id) {
    }

    public Page<CompetitionMemberTO> getMembers(UUID id, int page, int size) {
        return null;
    }

    public Page<CompetitionTO> getCompetitionsByProfile(UUID id, int page, int size) {
        return null;
    }

    public CompetitionTO getById(UUID id) {
        return null;
    }

    public Page<CompetitionTO> search(String name, int page, int size) {
        return null;
    }

    public CompetitionTO update(CompetitionTO competition, UUID id) {
        return null;
    }
}
