package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.enums.Status;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.bean.CompetitionMemberBeanUtil;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.common.api.dto.CompetitionMemberSaveTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompetitionMemberService {

    @Autowired
    private CompetitionMemberRepository repository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionMemberBeanUtil beanUtil;

    @Autowired
    private UserCommonFeign feign;

    public Page<CompetitionMember> getMembers(UUID id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return repository.getMemberOfCompetition(id, pageRequest);
    }

    public Page<Competition> getCompetitionsByProfile(int id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "id");
        return repository.getByProfile(id, pageRequest);
    }

    public void exitMember(String token, UUID id) {
        // Testar admin apagando (bloquear)
        // Testar member apagando ele mesmo
        // Testar member apagando outro member (bloquear)
        // Testar owner apagando member (bloquear)
        // Testar owner apagando admin

        CompetitionMember competitionMember = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001));
        ProfileTO profileTO = feign.getProfileByToken(token);
        CompetitionMember owner = repository.getCreatorOfCompetition(competitionMember.getCompetition().getId(), Role.owner);


        if(competitionMember.getRole() == Role.admin){
            if (profileTO.getId() != owner.getProfileId())
                throw new ResourceUnauthorizedException(CodeException.CM005.getText(), CodeException.CM005);

        }
        else if(profileTO.getId() != competitionMember.getProfileId()){
            throw new ResourceUnauthorizedException(CodeException.CM002.getText(), CodeException.CM002);
        }
        repository.deleteById(id);
    }

    public CompetitionMemberTO getById(UUID id) {
        return beanUtil.toReturnDTO(repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    public CompetitionMemberTO updateMember(String token, CompetitionMemberSaveTO memberTO, UUID id) {
        CompetitionMember member = beanUtil.toDomain(memberTO);
        ProfileTO profileTO = feign.getProfileByToken(token);
        CompetitionMember requester = repository.getByProfileIdAndCompetition(profileTO.getId(), member.getCompetition())
          .orElseThrow(() -> new ResourceNotFoundException(CodeException.CM003.getText(), CodeException.CM003));
        if(requester.getRole() != Role.member ){
            return updateByAdmin(member, id);
        }else{
            return updateByProfile(member, id, profileTO);
        }
    }

    private void verifyDate(CompetitionMember member) {
        if(LocalDateTime.now().isBefore(member.getCompetition().getSubscriptionDate())){
            throw new ResourceConflictException(CodeException.DA004.getText());
        }
        if(LocalDateTime.now().isAfter(member.getCompetition().getSubscriptionFinalDate())){
            throw new ResourceConflictException(CodeException.DA005.getText());
        }
    }

    public CompetitionMemberTO saveMember(String token, CompetitionMemberSaveTO memberTO) {
        feign.getProfile(memberTO.getProfileId());
        ProfileTO requester = feign.getProfileByToken(token);
        verifyIfProfileIsInCompetition(memberTO);
        CompetitionMemberTO savedMember;

        if(memberTO.getRole().equals(Role.admin))
            savedMember = createAdmin(memberTO, requester);
        else
            savedMember = createMember(memberTO, requester);

        return savedMember;
    }

    private CompetitionMemberTO createMember(CompetitionMemberSaveTO memberTO, ProfileTO requester) {
        CompetitionMember member = beanUtil.toDomain(memberTO);
        if(requester.getId() != memberTO.getProfileId()){
            throw new ResourceUnauthorizedException(CodeException.CM006.getText(), CodeException.CM006);
        }

        verifyDate(member);
        return beanUtil.toReturnDTO(repository.save(member));
    }

    private CompetitionMemberTO createAdmin(CompetitionMemberSaveTO memberTO, ProfileTO requester) {
        CompetitionMember owner = repository.getCreatorOfCompetition(memberTO.getCompetitionId(), Role.owner);

        if(requester.getId() != owner.getProfileId()){
            throw new ResourceUnauthorizedException(CodeException.CM005.getText(), CodeException.CM005);
        }
        return beanUtil.toReturnDTO(repository.save(beanUtil.toDomain(memberTO)));
    }

    private void verifyIfProfileIsInCompetition(CompetitionMemberSaveTO memberTO) {

        Competition competition = competitionRepository.findById(memberTO.getCompetitionId())
                .orElseThrow( () -> new ResourceConflictException(CodeException.CP001.getText(), CodeException.CP001));
        boolean member = repository.getByProfileIdAndCompetition(memberTO.getProfileId(), competition).isPresent();
        if(member){
            throw new ResourceConflictException(CodeException.CM004.getText(), CodeException.CM004);
        }
    }

    private CompetitionMemberTO updateByProfile(CompetitionMember member, UUID id, ProfileTO profileTO) {
        if(profileTO.getId() != member.getProfileId()){
            throw new ResourceUnauthorizedException(CodeException.CM006.getText(), CodeException.CM006);
        }
        verifyDate(member);
        return beanUtil.toReturnDTO(repository.findById(id).map(member1 -> {
            member1.setStory(member.getStory());
            member1.setTitle(member.getTitle());
            return repository.save(member1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    private CompetitionMemberTO updateByAdmin(CompetitionMember member, UUID id) {
        return beanUtil.toReturnDTO(repository.findById(id).map(member1 -> {
            member1.setStatus(member.getStatus());
            return repository.save(member1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    public List<CompetitionMemberTO> getMembersByRoleAndStatus(UUID competitionId, Role role, Status status) {

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CP001.getText(), CodeException.CP001));

        return  beanUtil.toDtoList(
                repository.getByCompetitionAndRoleAndStatus(competition, role, status));
    }
}
