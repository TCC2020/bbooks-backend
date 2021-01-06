package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.bean.TrackingBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.users.api.domain.Tracking;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.users.api.repository.TrackingRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class TrackingService {

    @Autowired
    private UserBooksRepository userBooksRepository;

    @Autowired
    private UserBooksService userBooksService;

    @Autowired
    private UserBooksBeanUtil userBooksBeanUtil;

    @Autowired
    private TrackingRepository repository;

    @Autowired
    private ReadingTrackingService readingTrackingService;

    @Autowired
    private TrackingBeanUtil beanUtil;

    public List<TrackingTO> getAllByBook(Long userBook) {
        List<TrackingTO> trackingTOS = beanUtil.toDTO(repository.findAllByUserBookOrderByCreationDate(
                userBooksRepository.findById(userBook)
                        .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001))));
        trackingTOS.forEach(trackingTO -> trackingTO.setVelocidadeLeitura(calcularVelocidadeLeitura(trackingTO)));
        return trackingTOS;
    }

    public TrackingTO save(@Valid TrackingTO trackingTO) {
        UserBooks userBooks = getUserBook(trackingTO);
        Tracking tracking = beanUtil.toDomain(trackingTO);
        tracking.setUserBook(userBooks);
        this.verifingTrackings(trackingTO.getUserBookId());
        this.verificaStatusLivro(tracking.getUserBook());

        return beanUtil.toDTO(repository.save(tracking));
    }

    public TrackingTO findById(UUID trackingId){
        TrackingTO trackingTO = beanUtil.toDTO(repository.findById(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002)));
        trackingTO.setVelocidadeLeitura(calcularVelocidadeLeitura(trackingTO));
        return trackingTO;
    }

    public void deleteById(UUID trackingId){
        repository.findById(trackingId)
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

    protected void verifingTrackings(Long userBookId){
        List<Tracking> trackingsBooks = repository.findAllByUserBookOrderByCreationDate(
                userBooksRepository.findById(userBookId)
                        .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001)));

        if(trackingsBooks.stream().filter( t -> t.getFinishedDate() == null).count() > 0){
            throw new ResourceConflictException(CodeException.TA001.getText(), CodeException.TA001);
        }

    }

    protected Tracking update(Tracking readingTracking) {
        System.out.print(readingTracking.toString());
        return repository.findById(readingTracking.getId()).map( readingTracking1 -> {
            readingTracking1.setComentario(readingTracking.getComentario());
            readingTracking1.setCreationDate(readingTracking.getFinishedDate());
            return repository.save(readingTracking1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));

    }

    protected Tracking getOne(UUID trackinkId){
        return repository.findById(trackinkId).orElseThrow(() -> new ResourceNotFoundException(CodeException.TA002.getText(), CodeException.TA002));
    }

    private void verificaStatusLivro(UserBooks userBooks) {
        if(userBooks.getStatus() == Status.QUERO_LER){
            updateUserBooksStatus(userBooks, Status.LENDO);
        }
        if(userBooks.getStatus() == Status.LIDO){
            updateUserBooksStatus(userBooks, Status.RELENDO);
        }
    }

    private void updateUserBooksStatus(UserBooks userBooks, Status status) {
        UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
        booK.setStatus(status.name());
        userBooksService.updateStatus(booK);
    }

    public Double calcularVelocidadeLeitura(TrackingTO tracking) {
        int qtdeDias = 1;
        double qtdePaginasLidas = 0;

        if(tracking.getTrackings().size() == 1) {
            qtdePaginasLidas = tracking.getTrackings().get(0).getNumPag();
        } else {

            for (int i = 1; i < tracking.getTrackings().size(); i++) {
                if (!tracking.getTrackings().get(i).getCreationDate().toLocalDate().equals(tracking.getTrackings().get(i - 1).getCreationDate().toLocalDate())) {
                    qtdeDias++;
                }
                qtdePaginasLidas = tracking.getTrackings().get(i).getNumPag();
            }
        }

        return qtdePaginasLidas/qtdeDias;
    }

}
