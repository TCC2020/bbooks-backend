package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CompetitionEndService {

    @Autowired
    private CompetitionRepository repository;

    @Autowired
    private CompetitionMemberRepository memberRepository;

    @Autowired
    private CompetitionVoteService voteService;

    @Scheduled(cron = "0 1 1 * * ?")
    public void competitionVerifyDate(){
        List<Competition> competitions = repository.findCompetitionOpen();

        competitions.stream().filter( z -> z.getFinalDate().isEqual(LocalDate.now()))
                .forEach(z -> endCompetition(z));
    }

    public void endCompetition(Competition competition){
        List<CompetitionMember> membros = memberRepository.getByCompetition(competition);

        UUID winnerCompetitor = getWinner(toHashMap(membros));
        setWinner(winnerCompetitor, competition);
    }

    private HashMap<UUID, Float> toHashMap(List<CompetitionMember> membros) {
        HashMap<UUID, Float> map = new HashMap<>();
        membros.forEach( m -> {
            map.put(m.getMemberId(), voteService.average(m));
        });
        return map;
    }

    public UUID getWinner(HashMap<UUID, Float> map){
        return map.entrySet().stream().max((e1, e2) ->e1.getValue()> e2.getValue()?1:-1).get().getKey();
    }

    private void setWinner(UUID competitionMemberId, Competition competition){
        CompetitionMember member = memberRepository.findById(competitionMemberId).orElseThrow(() -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001));
        competition.setWinnerProfile(member);
        repository.save(competition);
    }
}
