package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.ReadingTrackingBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.TrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import com.google.rpc.Code;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class ReadingTrackingService {

    @Autowired
    ReadingTrackingRepository repository;

    @Autowired
    ReadingTrackingBeanUtil beanUtil;

    @Autowired
    UserBooksRepository userBooksRepository;

    @Autowired
    UserBooksService userBooksService;

    @Autowired
    UserBooksBeanUtil userBooksBeanUtil;

    @Autowired
    TrackingService trackingService;

    public ReadingTrackingTO get(UUID readingTracking) {

        return beanUtil.toDTO(repository.findById(readingTracking)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)));
    }

    public ReadingTrackingTO save(@Valid ReadingTrackingTO readingTrackingTO) {

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);
        UserBooks userBooks = this.getUserBook(readingTrackingTO.getUserBookId());
        this.verificaStatusLivro(userBooks);
        readingTracking.setPercentage(calcularPercentual(readingTracking, userBooks, readingTrackingTO.getTrackingUpId() ));

        return beanUtil.toDTO(saveReadingTracking(readingTrackingTO, readingTracking));
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


    public void delete(UUID trackingID) {
        ReadingTracking tracking = repository.findById(trackingID)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));

        repository.delete(tracking);
    }

    private void verificaStatusLivro(UserBooks userBooks) {
        if(userBooks.getStatus() == UserBooks.Status.QUERO_LER){
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
            booK.setStatus(UserBooks.Status.LENDO.name());
            userBooksService.updateStatus(booK);
        }
        if(userBooks.getStatus() == UserBooks.Status.LIDO){
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
            booK.setStatus(UserBooks.Status.RELENDO.name());
            userBooksService.updateStatus(booK);
        }
    }

    private float calcularPercentual(ReadingTracking readingTracking, UserBooks userBooks, UUID trackinkUpID) {
        int paginasTotais = userBooks.getPage() != 0 ? userBooks.getPage(): userBooks.getBook().getNumberPage();

        verificaPaginas( readingTracking.getNumPag(), paginasTotais, getPaginasAnteriores(trackinkUpID));

        float percentual = readingTracking.getNumPag() * 100F / paginasTotais;

        verificaPercentual(trackinkUpID, percentual);

        return percentual ;
    }

    private UserBooks getUserBook(Long userBookId) {
        return userBooksRepository.findById(userBookId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
    }

    private void getReadingTracking(ReadingTrackingTO readingTrackingTO, UUID readingTrackingID) {
        if(!readingTrackingID.equals(readingTrackingTO.getId()))
            throw new ResourceConflictException(CodeException.RT004.getText(), CodeException.RT001);
    }

    private ReadingTracking saveReadingTracking(@Valid ReadingTrackingTO readingTrackingTO, ReadingTracking readingTracking) {
        ReadingTracking response =repository.save(readingTracking);

        trackingService.addReadingTracking(readingTrackingTO, response);
        return response;
    }

    private int getPaginasAnteriores(UUID trackinkUpID) {
        List<ReadingTracking> lista = trackingService.getOne(trackinkUpID).getTrackings();
        return lista.size() > 0? lista.get(lista.size()-1).getNumPag() : 0;
    }

    private void verificaPaginas(int paginasLidas, int paginasTotais, int ultimasPaginasLidas) {
        if(paginasLidas < ultimasPaginasLidas)
            throw new ResourceConflictException(CodeException.RT005.getText(), CodeException.RT005);
        else if(paginasLidas>paginasTotais)
            throw new ResourceConflictException(CodeException.RT002.getText(), CodeException.RT002);
    }

    private void verificaPercentual(UUID trackinkID, float percentual) {
        if(percentual == 100.00F){
            Tracking tracking = trackingService.getOne(trackinkID);
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(tracking.getUserBook());
            booK.setStatus(UserBooks.Status.LIDO.name());
            userBooksService.updateStatus(booK);
            tracking.setFinishedDate(LocalDateTime.now());
            trackingService.update(tracking);
        }
    }
}
