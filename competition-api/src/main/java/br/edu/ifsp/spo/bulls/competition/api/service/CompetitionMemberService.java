package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.enums.Role;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.bean.CompetitionMemberBeanUtil;
import br.edu.ifsp.spo.bulls.competition.api.domain.Competition;
import br.edu.ifsp.spo.bulls.competition.api.domain.CompetitionMember;
import br.edu.ifsp.spo.bulls.competition.api.dto.CompetitionMemberTO;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionMemberRepository;
import br.edu.ifsp.spo.bulls.competition.api.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        // TODO: Testar admin apagando
        // TODO: Testar member apagando ele mesmo
        // TODO: Testar member apagando outro member (bloquear)
        // TODO: Testar owner apagando

        CompetitionMember competitionMember = repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001));

        ProfileTO profileTO = feign.getProfileByToken(token);
        CompetitionMember requester = repository.getByProfileIdAndCompetition(profileTO.getId(), competitionMember.getCompetition())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.CM003.getText(), CodeException.CM003));

        // TODO: Verificar se caso for um admin ou o owner pode excluir
        if( (requester.getRole() == Role.member) && (profileTO.getId() != competitionMember.getProfileId()) ){
            throw new ResourceUnauthorizedException(CodeException.CM002.getText(), CodeException.CM002);
        }
        repository.deleteById(id);
    }

    public CompetitionMemberTO getById(UUID id) {
        return beanUtil.toDto(repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    public CompetitionMemberTO updateMember(String token, CompetitionMemberTO memberTO, UUID id) {
        // TODO: Testar member editando cadastro dele
        // TODO: Testar member editando outro member (bloquear)
        // TODO: Testar owner editando
        // TODO: Testar admin editando

        CompetitionMember member = beanUtil.toDomain(memberTO);
        ProfileTO profileTO = feign.getProfileByToken(token);

        if(profileTO.getId() != memberTO.getProfileId() ){
            return updateByAdmin(member, id);
        }else{
            return updateByProfile(member, id);
        }
    }

    public CompetitionMemberTO saveMember(String token,CompetitionMemberTO memberTO) {
        // Testar profile adicionando ele mesmo
        // Testar profile adicionando outro profile (bloquear)
        // TODO: Testar owner adicionando admin -> está parando no id que não bate, deveria deixar
        // TODO: Testar admin adicionando outro admin (bloquear)
        // TODO: Testar member adicionando admin (bloquear) -> está parando no id que não bate, arrumar mensagem
        // TODO: Testar owner adicionando member -> está parando no id que não bate, deveria deixar
        // TODO: implementar requisições
        // TODO: Verificar se o profile existe

        ProfileTO profileTO = feign.getProfileByToken(token);
        CompetitionMember owner = repository.getCreatorOfCompetition(memberTO.getCompetitionId(), Role.owner);

        verifyIfProfileIsInCompetition(memberTO);

        System.out.println(owner);
        System.out.println(profileTO);
        if(memberTO.getRole().equals(Role.admin) && owner.getProfileId() != profileTO.getId() ) {
        System.out.println("passou aqui");
            throw new ResourceUnauthorizedException(CodeException.CM005.getText(), CodeException.CM005);
        }
        else if( profileTO.getId() != memberTO.getProfileId())
        {
            throw new ResourceUnauthorizedException(CodeException.CM002.getText(), CodeException.CM002);
        }

        return beanUtil.toDto(repository.save(beanUtil.toDomain(memberTO)));
    }

    private void verifyIfProfileIsInCompetition(CompetitionMemberTO memberTO) {
        Competition competition = competitionRepository.findById(memberTO.getCompetitionId())
                .orElseThrow( () -> new ResourceConflictException(CodeException.CP001.getText(), CodeException.CP001));
        boolean member = repository.getByProfileIdAndCompetition(memberTO.getProfileId(), competition).isPresent();
        if(member){
            throw new ResourceConflictException(CodeException.CM004.getText(), CodeException.CM004);
        }
    }

    private CompetitionMemberTO updateByProfile(CompetitionMember member, UUID id) {
        return beanUtil.toDto(repository.findById(id).map( member1 -> {
            member1.setStory(member.getStory());
            member1.setTitle(member.getTitle());
            return repository.save(member1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }

    private CompetitionMemberTO updateByAdmin(CompetitionMember member, UUID id) {
        return beanUtil.toDto(repository.findById(id).map( member1 -> {
            member1.setStatus(member.getStatus());
            return repository.save(member1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.CM001.getText(), CodeException.CM001)));
    }
}
