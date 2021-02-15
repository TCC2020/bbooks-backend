package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.competition.api.bean.CompetitionBeanUtill;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionTO;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository repository;

    @Autowired
    private CompetitionBeanUtill beanUtil;

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<CompetitionMemberTO> getMembers(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return null;
    }

    public Page<CompetitionTO> getCompetitionsByProfile(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return null;
    }

    public CompetitionTO getById(UUID id) {
        return beanUtil.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001)));
    }

    public Page<CompetitionTO> search(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return null;
    }

    public CompetitionTO update(CompetitionTO competitionTO, UUID id) {
        Competition competition = beanUtil.toDomain(competitionTO);

        return beanUtil.toDto(repository.findById(id).map( competition1 -> {
            competition1 = competition;
            return repository.save(competition1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001)));
    }

    public CompetitionTO save(CompetitionTO competition) {
        return beanUtil.toDto(repository.save(beanUtil.toDomain(competition)));
    }
}
