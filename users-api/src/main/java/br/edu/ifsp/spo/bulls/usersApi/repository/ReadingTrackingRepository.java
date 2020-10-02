package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.UUID;

@Repository
public interface ReadingTrackingRepository extends CrudRepository<ReadingTracking, UUID> {

    HashSet<ReadingTracking> findAllByUserBook (UserBooks userBooks);

}
