package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.ReadingTrackingBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.HashSet;
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

    public HashSet<ReadingTrackingTO> getAllByBook(Long userBook) {
        return beanUtil.toDTO(repository.findAllByUserBook(
                userBooksRepository.findById(userBook)
                                    .orElseThrow(() -> new ResourceNotFoundException("Userbooks not found"))));
    }

    public ReadingTrackingTO get(UUID readingTracking) {

        return beanUtil.toDTO(repository.findById(readingTracking)
                .orElseThrow( () -> new ResourceNotFoundException("Tracking not found")));
    }

    public ReadingTrackingTO save(@Valid ReadingTrackingTO readingTrackingTO) {
        UserBooks userBooks = userBooksRepository.findById(readingTrackingTO.getUserBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Userbooks not found"));

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);
        verificaStatusLivro(userBooks);

        readingTracking.setUserBook(userBooks);
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
            throw new ResourceConflictException("Livro já está concluído");
        }
    }

    private float calcularPercentual(ReadingTracking readingTracking) {
        int paginasTotais;
        if(readingTracking.getUserBook().getBook() != null){
            paginasTotais = readingTracking.getUserBook().getBook().getNumberPage();
        }else{
            paginasTotais = readingTracking.getUserBook().getPage();
        }
        float percentual = readingTracking.getNumPag() * 100 / paginasTotais;
        verificaPercentual(readingTracking.getUserBook(), percentual);
        return percentual ;
    }

    private void verificaPercentual(UserBooks userBook, float percentual) {
        if(percentual == 100.00F){
            UserBookUpdateStatusTO booK = userBooksBeanUtil.toDTOUpdate(userBook);
            booK.setStatus(UserBooks.Status.LIDO.name());
            userBooksService.updateStatus(booK);
        }
    }

    public ReadingTrackingTO update(ReadingTrackingTO readingTrackingTO, UUID trackingID) {
        ReadingTracking reading = repository.findById(readingTrackingTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ReadingTracking not found"));

        UserBooks userBooks = userBooksRepository.findById(readingTrackingTO.getUserBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Userbooks not found"));

        ReadingTracking readingTracking = beanUtil.toDomain(readingTrackingTO);

        readingTracking.setUserBook(userBooks);
        readingTracking.setPercentage(calcularPercentual(readingTracking));

        return beanUtil.toDTO(repository.findById(readingTracking.getId()).map( readingTracking1 -> {
            readingTracking1.setId(readingTracking.getId());
            readingTracking1.setPercentage(readingTracking.getPercentage());
            readingTracking1.setComentario(readingTracking.getComentario());
            readingTracking1.setNumPag(readingTracking.getNumPag());
            return repository.save(readingTracking1);
        }).orElseThrow( () -> new ResourceNotFoundException("ReadingTracking not found")));

    }

    public void delete(UUID trackingID) {
        ReadingTracking tracking = repository.findById(trackingID)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking not found"));

        repository.delete(tracking);
    }
}
