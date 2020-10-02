package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import com.google.api.gax.rpc.NotFoundException;
import com.sun.xml.bind.v2.runtime.output.StAXExStreamWriterOutput;
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
    UserBooksRepository userBooksRepository;

    public HashSet<ReadingTracking> getAllByBook(Long userBook) {
        return repository.findAllByUserBook(
                userBooksRepository.findById(userBook)
                                    .orElseThrow(() -> new ResourceNotFoundException("Userbooks not found")));
    }

    public ReadingTracking get(UUID readingTracking) {

        return repository.findById(readingTracking)
                .orElseThrow( () -> new ResourceNotFoundException("Tracking not found"));
    }

    public ReadingTracking save(@Valid ReadingTracking readingTracking) {
        UserBooks userBooks = userBooksRepository.findById(readingTracking.getUserBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Userbooks not found"));

        readingTracking.setUserBook(userBooks);
        readingTracking.setPercentage(calcularPercentual(readingTracking));

        return repository.save(readingTracking);

    }

    private float calcularPercentual(ReadingTracking readingTracking) {
        float dividendo =  readingTracking.getNumPag() * 100;
        System.out.println(dividendo);
        System.out.println(readingTracking.toString());
        System.out.println(readingTracking.getUserBook().getPage());
        return  dividendo / readingTracking.getUserBook().getPage();
    }

    public ReadingTracking update(ReadingTracking readingTracking, UUID trackingID) {
        ReadingTracking reading = repository.findById(readingTracking.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ReadingTracking not found"));

        readingTracking.setPercentage(calcularPercentual(readingTracking));

        return repository.findById(readingTracking.getId()).map( readingTracking1 -> {
            readingTracking1.setId(readingTracking.getId());
            readingTracking1.setPercentage(readingTracking.getPercentage());
            readingTracking1.setComentario(readingTracking.getComentario());
            readingTracking1.setNumPag(readingTracking.getNumPag());
            return repository.save(readingTracking1);
        }).orElseThrow( () -> new ResourceNotFoundException("ReadingTracking not found"));

    }

    public void delete(UUID trackingID) {
        ReadingTracking tracking = repository.findById(trackingID)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking not found"));

        repository.delete(tracking);
    }
}
