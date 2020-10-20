package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.TrackingBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.TrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class TrackingService {

    @Autowired
    UserBooksRepository userBooksRepository;

    @Autowired
    UserBooksService userBooksService;

    @Autowired
    UserBooksBeanUtil userBooksBeanUtil;

    @Autowired
    TrackingRepository repository;

    @Autowired
    ReadingTrackingService readingTrackingService;

    @Autowired
    TrackingBeanUtil beanUtil;

    public List<TrackingTO> getAllByBook(Long userBook) {
        return beanUtil.toDTO(repository.findAllByUserBookOrderByCreationDate(
                userBooksRepository.findById(userBook)
                        .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001))));
    }

    public TrackingTO save(@Valid TrackingTO trackingTO) {
        UserBooks userBooks = getUserBook(trackingTO);
        Tracking tracking = beanUtil.toDomain(trackingTO);
        tracking.setUserBook(userBooks);
        this.verifingTrackings(trackingTO.getUserBookId());
        this.verificaStatusLivro(tracking.getUserBook());

        return beanUtil.toDTO(repository.save(tracking));
    }

    public void deleteById(UUID trackingId){
        Tracking tracking = repository.findById(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));

        deleteChildrens(trackingId);

        repository.deleteById(trackingId);
    }

    private void deleteChildrens(UUID trackingId) {
        for(ReadingTracking readingTracking : readingTrackingService.getByTrackingGroup(trackingId)){
            readingTrackingService.delete(readingTracking.getId());
        }
    }

    public TrackingTO update(UUID trackingId, TrackingTO trackingTO){

        return beanUtil.toDTO(repository.findById(trackingId).map( tracking1 -> {
            tracking1.setComentario(trackingTO.getComentario());
            return repository.save(tracking1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)));
    }

    private UserBooks getUserBook(TrackingTO trackingTO) {
        return userBooksRepository.findById(trackingTO.getUserBookId())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
    }

    private void verifingTrackings(Long userBookId){
        List<Tracking> trackingsBooks = repository.findAllByUserBookOrderByCreationDate(
                userBooksRepository.findById(userBookId)
                        .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001)));

        if(trackingsBooks.stream().filter( t -> t.getFinishedDate() == null).count() > 0){
            throw new ResourceNotFoundException(CodeException.TA001.getText(), CodeException.TA001);
        }

    }

    protected Tracking update(Tracking readingTracking) {
        return repository.findById(readingTracking.getId()).map( readingTracking1 -> {
            readingTracking1.setComentario(readingTracking.getComentario());
            readingTracking1.setCreationDate(readingTracking.getFinishedDate());
            return repository.save(readingTracking1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));

    }

    protected Tracking getOne(UUID trackinkId){
        return repository.findById(trackinkId).orElseThrow(() -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));
    }

    private void verificaStatusLivro(UserBooks userBooks) {
        if(userBooks.getStatus() == UserBooks.Status.QUERO_LER){
            updateUserBooksStatus(userBooks, UserBooks.Status.LENDO);
        }
        if(userBooks.getStatus() == UserBooks.Status.LIDO){
            updateUserBooksStatus(userBooks, UserBooks.Status.RELENDO);
        }
    }

    private void updateUserBooksStatus(UserBooks userBooks, UserBooks.Status status) {
        UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
        booK.setStatus(status.name());
        userBooksService.updateStatus(booK);
    }

}
