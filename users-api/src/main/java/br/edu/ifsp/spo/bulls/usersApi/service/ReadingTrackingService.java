package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.ReadingTrackingBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReadingTrackingService {

    @Autowired
    ReadingTrackingRepository repository;

    @Autowired
    ReadingTrackingBeanUtil beanUtil;

    @Autowired
    UserBooksService userBooksService;

    @Autowired
    UserBooksBeanUtil userBooksBeanUtil;

    @Autowired
    TrackingService trackingService;


    public List<ReadingTracking> getByTrackingGroup(UUID trackingGroup){
        return repository.findByTrackingGroup(trackingService.getOne(trackingGroup));
    }

    public ReadingTrackingTO get(UUID readingTracking) {

        return beanUtil.toDTO(repository.findById(readingTracking)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)));
    }

    public ReadingTrackingTO save(@Valid ReadingTrackingTO readingTrackingTO) {

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);

        readingTracking.setPercentage(calcularPercentual(readingTracking));

        return beanUtil.toDTO(repository.save(readingTracking));
    }

    public ReadingTrackingTO update(ReadingTrackingTO readingTrackingTO, UUID trackingID) {
        getReadingTracking(readingTrackingTO, trackingID);

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);

        return beanUtil.toDTO(repository.findById(readingTracking.getId()).map( readingTracking1 -> {
            readingTracking1.setId(readingTracking.getId());
            readingTracking1.setComentario(readingTracking.getComentario());
            return repository.save(readingTracking1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)));

    }


    public void delete(UUID readingTrackingID) {
        ReadingTracking readingTracking = repository.findById(readingTrackingID)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));

        if(readingTracking.getPercentage() == 100.0){
            this.updateFinishedDate(readingTracking.getTrackingGroup(), null);
            this.updateUserBooksStatus(readingTracking.getTrackingGroup().getUserBook(), UserBooks.Status.LENDO);
        }

        repository.delete(readingTracking);
    }





    private float calcularPercentual(ReadingTracking readingTracking) {
        int paginasTotais = readingTracking.getTrackingGroup().getUserBook().getPage() != 0
                ? readingTracking.getTrackingGroup().getUserBook().getPage()
                : readingTracking.getTrackingGroup().getUserBook().getBook().getNumberPage();

        verificaPaginas( readingTracking.getNumPag(), paginasTotais, getPaginasAnteriores(readingTracking.getTrackingGroup()));

        float percentual = readingTracking.getNumPag() * 100F / paginasTotais;

        verificaPercentual(readingTracking.getTrackingGroup(), percentual);

        return percentual ;
    }



    private void getReadingTracking(ReadingTrackingTO readingTrackingTO, UUID readingTrackingID) {
        if(!readingTrackingID.equals(readingTrackingTO.getId()))
            throw new ResourceConflictException(CodeException.RT004.getText(), CodeException.RT001);
    }

    private int getPaginasAnteriores(Tracking trackingGroup) {

        List<ReadingTracking> lista = repository.findByTrackingGroup(trackingGroup);
        return lista.size() > 0? lista.get(lista.size()-1).getNumPag() : 0;
    }

    private void verificaPaginas(int paginasLidas, int paginasTotais, int ultimasPaginasLidas) {
        if(paginasLidas < ultimasPaginasLidas)
            throw new ResourceConflictException(CodeException.RT005.getText(), CodeException.RT005);
        else if(paginasLidas>paginasTotais)
            throw new ResourceConflictException(CodeException.RT002.getText(), CodeException.RT002);
    }

    private void verificaPercentual(Tracking trackingGroup, float percentual) {
        if(percentual == 100.00F){
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(trackingGroup.getUserBook());
            booK.setStatus(UserBooks.Status.LIDO.name());
            userBooksService.updateStatus(booK);
            updateFinishedDate(trackingGroup, LocalDateTime.now());
        }
    }

    private void updateFinishedDate(Tracking trackingGroup, LocalDateTime dateTime) {
        trackingGroup.setFinishedDate(dateTime);
        trackingService.update(trackingGroup);
    }

    private void updateUserBooksStatus(UserBooks userBooks, UserBooks.Status status) {
        UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
        booK.setStatus(status.name());
        userBooksService.updateStatus(booK);
    }
}
