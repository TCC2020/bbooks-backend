package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.users.api.domain.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReadingTrackingRepository extends JpaRepository<ReadingTracking, UUID> {

    List<ReadingTracking> findByTrackingGroup (Tracking tracking);

}
