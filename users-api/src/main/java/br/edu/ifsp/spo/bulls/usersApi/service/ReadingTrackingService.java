package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.ReadingTrackingBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
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


    public ReadingTrackingTO get(UUID readingTracking) {

        return beanUtil.toDTO(repository.findById(readingTracking)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)));
    }

    public ReadingTrackingTO save(@Valid ReadingTrackingTO readingTrackingTO) {

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);

        readingTracking.setPercentage(calcularPercentual(readingTracking));

        return beanUtil.toDTO(repository.save(readingTracking));

    }

    private void verificaStatusLivro(UserBooks userBooks) {
        if(userBooks.getStatus() == UserBooks.Status.QUERO_LER){
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
            booK.setStatus(UserBooks.Status.LENDO.name());
            userBooksService.updateStatus(booK);
        }
        if(userBooks.getStatus() == UserBooks.Status.LIDO){
            throw new ResourceConflictException(CodeException.RT003.getText(), CodeException.RT003);
        }
    }

    private float calcularPercentual(ReadingTracking readingTracking) {
        int paginasTotais = 2;
        verificaPaginas(readingTracking);
//        if(readingTracking.getUserBook().getBook() != null){
//            paginasTotais = readingTracking.getUserBook().getBook().getNumberPage();
//        }else{
//            paginasTotais = readingTracking.getUserBook().getPage();
//        }
        float percentual = readingTracking.getNumPag() * 100F / paginasTotais;
//        verificaPercentual(readingTracking.getUserBook(), percentual);
        return percentual ;
    }

    private void verificaPaginas(ReadingTracking readingTracking) {
//        int page = readingTracking.getUserBook().getPage();
//        if(readingTracking.getUserBook().getBook() != null)
//            page = readingTracking.getUserBook().getBook().getNumberPage();
//
//        if(readingTracking.getNumPag()>page){
//            throw new ResourceConflictException(CodeException.RT002.getText(), CodeException.RT002);
//        }
    }

    private void verificaPercentual(UserBooks userBook, float percentual) {
        if(percentual == 100.00F){
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBook);
            booK.setStatus(UserBooks.Status.LIDO.name());
            userBooksService.updateStatus(booK);
        }
    }

    public ReadingTrackingTO update(ReadingTrackingTO readingTrackingTO, UUID trackingID) {
        getReadingTracking(readingTrackingTO);

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);

        readingTracking.setPercentage(calcularPercentual(readingTracking));

        return beanUtil.toDTO(repository.findById(readingTracking.getId()).map( readingTracking1 -> {
            if(readingTracking1.getPercentage() == 100.00F && readingTracking1.getNumPag() != readingTracking.getNumPag())
                //mudaStatusLendo(readingTracking1.getUserBook());

            readingTracking1.setId(readingTracking.getId());
            readingTracking1.setPercentage(readingTracking.getPercentage());
            readingTracking1.setComentario(readingTracking.getComentario());
            readingTracking1.setNumPag(readingTracking.getNumPag());
            return repository.save(readingTracking1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001)));

    }

    private void mudaStatusLendo(UserBooks userBooks) {
        UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBooks);
        booK.setStatus(UserBooks.Status.LENDO.name());
        userBooksService.updateStatus(booK);
    }

    private void getReadingTracking(ReadingTrackingTO readingTrackingTO) {
        ReadingTracking reading = repository.findById(readingTrackingTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));
    }

//    private UserBooks getUserBook(ReadingTrackingTO readingTrackingTO) {
//        return userBooksRepository.findById(readingTrackingTO.getUserBookId())
//                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
//    }

    public void delete(UUID trackingID) {
        ReadingTracking tracking = repository.findById(trackingID)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.RT001.getText(), CodeException.RT001));

        repository.delete(tracking);
    }
}
