package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.bean.CompetitionBeanUtill;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompetitionService {

    @Autowired
    private CompetitionRepository repository;

    @Autowired
    private CompetitionMemberRepository memberRepository;

    @Autowired
    private CompetitionVoteService voteService;

    @Autowired
    private CompetitionBeanUtill beanUtil;

    @Autowired
    private UserCommonFeign feign;

    public void delete(String token, UUID id) {
        Competition competition = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001));
        verifyProfileRequested(token, id);
        memberRepository.deleteAllByCompetition(competition);
        repository.deleteById(id);
    }

    public CompetitionTO getById(UUID id) {
        return beanUtil.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001)));
    }

    public Page<Competition> search(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");

        return repository.findByTitleOrRulesContaining(name, name, pageRequest);
    }

    public CompetitionTO update(String token, CompetitionTO competitionTO, UUID id) {
        verifyProfileRequested(token, id);
        verifyDate(competitionTO);
        Competition competition = beanUtil.toDomain(competitionTO);

        return beanUtil.toDto(repository.findById(id).map( competition1 -> {
            competition1 = competition;
            return repository.save(competition1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001)));
    }

    public CompetitionTO save(CompetitionTO competitionTO) {
        verifyDate(competitionTO);
        Competition competition = repository.save(beanUtil.toDomain(competitionTO));
        saveOwner(competitionTO, competition);

        return beanUtil.toDto(competition);
    }

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

    private void verifyDate(CompetitionTO competitionTO) {
        if(competitionTO.getSubscriptionDate().isBefore(LocalDateTime.now())){
            throw new ResourceConflictException(CodeException.DA001.getText());
        }
        if(competitionTO.getSubscriptionDate().isAfter(competitionTO.getSubscriptionFinalDate())){
            throw new ResourceConflictException(CodeException.DA002.getText());
        }
        if(competitionTO.getFinalDate().isBefore(competitionTO.getSubscriptionFinalDate())){
            throw new ResourceConflictException(CodeException.DA003.getText());
        }
    }

    private void saveOwner(CompetitionTO competitionTO, Competition competition) {
        CompetitionMember member = new CompetitionMember();
        member.setProfileId(competitionTO.getCreatorProfile());
        member.setRole(Role.owner);
        member.setCompetition(competition);
        memberRepository.save(member);
    }

    private void verifyProfileRequested(String token, UUID competitonId){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        CompetitionMember competitionMember = memberRepository.getCreatorOfCompetition(competitonId, Role.owner);
        ProfileTO profileTO = feign.getProfileByToken(tokenValue);
        if(profileTO.getId() != competitionMember.getProfileId() ){
            throw new ResourceUnauthorizedException(CodeException.CM002.getText(), CodeException.CM002);
        }
    }
}
