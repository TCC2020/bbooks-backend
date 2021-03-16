package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.BookMonthTO;
import br.edu.ifsp.spo.bulls.common.api.dto.SurveyOptionsTO;
import br.edu.ifsp.spo.bulls.common.api.dto.SurveyTO;
import br.edu.ifsp.spo.bulls.common.api.dto.VoteTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.BookMonthBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.bean.SurveyBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.BookMonth;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.domain.Survey;
import br.edu.ifsp.spo.bulls.feed.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyBeanUtil surveyBeanUtil;
    @Autowired
    private SurveyOptionsRepository surveysOptionsRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private BookMonthRepository bookMonthRepository;
    @Autowired
    private BookMonthBeanUtil monthBeanUtil;


    @Autowired
    private SurveyBeanUtil beanUtil;

    public void save(Survey survey, List<SurveyOptionsTO> optionsTO) {
        // TODO:? Excluir uma opção quando ela n vier para ser salva/alterada
        optionsTO.stream().forEach( x -> surveysOptionsRepository.save(beanUtil.toSurveyOptionDomain(x, survey)));
    }

    public void deleteByPost(UUID idPost) {
        Post post = postRepository.findById(idPost).get();
        surveysOptionsRepository.deleteAllBySurvey(post.getSurvey());
    }

    // public PostReactionTO react(String token, ReactTO reactionTO) {
    // tentar fazer tudo de voto em um método
    public VoteTO vote(int profileId, VoteTO voteTO){
        // TODO: Implementar
        return null;
    }

    public VoteTO updateVote(int profileId, VoteTO voteTO, UUID voteId){
        // TODO: Implementar
        return null;
    }

    public void deleteVote(UUID voteId) {
        // TODO: Implementar
    }

    public int getVotesBySurvey(UUID surveyId) {
        // TODO: Implementar
        return 0;
    }

    public VoteTO getVoteByProfileAndOption(int profileId, UUID optionId){
        // TODO: Implementar
        return null;
    }

    public List<BookMonthTO> getBookByGroup(UUID groupId) {
        List<BookMonth> bookMonths = bookMonthRepository.findByGroupId(groupId);
        return monthBeanUtil.toDto(bookMonths);
    }

    public BookMonthTO saveBookMonth(BookMonthTO bookMonthTO) {
        // TODO: Verificar se tem algum pro mês/ano
        // TODO: Verificar admin
        BookMonth bookMonth = monthBeanUtil.toDomain(bookMonthTO);
        return monthBeanUtil.toDto(bookMonthRepository.save(bookMonth));
    }

    public void deleteBookMonth(UUID bookMonthId) {
        // TODO: Verificar admin
        bookMonthRepository.deleteById(bookMonthId);
    }

    public void deleteBookByGroup(UUID groupId) {
        bookMonthRepository.deleteByGroupId(groupId);
    }

    public BookMonthTO getBookMonthById(UUID bookId) {
        return monthBeanUtil.toDto(bookMonthRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.BKM001.getText(), CodeException.BKM001)));
    }
}
