package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompetitionMemberService {

    public Page<CompetitionMemberTO> getMembers(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");

        //TODO: implementar
        return null;
    }

    public Page<CompetitionTO> getCompetitionsByProfile(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        //TODO: implementar
        return null;
    }

    public void exitMember(UUID id) {
        //TODO: implementar
    }

    public CompetitionMemberTO getById(UUID id) {
        //TODO: implementar
        return null;
    }

    public CompetitionMemberTO updateMember(CompetitionMemberTO memberTO, UUID id) {
        //TODO: implementar
        return null;
    }

    public CompetitionMemberTO saveMember(CompetitionMemberTO memberTO) {
        //TODO: implementar
        //TODO: implementar requisições e status
        return null;
    }
}
