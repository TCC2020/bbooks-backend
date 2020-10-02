package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import com.google.api.gax.rpc.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
public class ReadingTrackingService {

    @Autowired
    ReadingTrackingRepository repository;

    @Autowired
    UserBooksRepository userBooksRepository;

    public HashSet<ReadingTracking> getAllByBook(Long userBook) {
        return repository.getAllByBook(userBooksRepository.findById(userBook));
    }

    public ReadingTracking get(UUID readingTracking) {

        return repository.findById(readingTracking)
                .orElseThrow( () -> new ResourceNotFoundException(""));
    }

    public ReadingTracking save(ReadingTracking readingTracking) {
    }

    public ReadingTracking update(ReadingTracking readingTracking, UUID trackingID) {
    }

    public ReadingTracking delete(UUID trackingID) {
    }
}
