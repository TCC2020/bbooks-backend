package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, UUID>{

    List<Tracking> findAllByUserBookOrderByCreationDate (UserBooks userBooks);

}
