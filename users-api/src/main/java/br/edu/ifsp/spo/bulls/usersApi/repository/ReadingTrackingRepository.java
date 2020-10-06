package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReadingTrackingRepository extends JpaRepository<ReadingTracking, UUID> {

    List<ReadingTracking> findAllByUserBookOrderByCreationDate (UserBooks userBooks);

}
