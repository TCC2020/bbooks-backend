package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.PublicProfileFollowers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowersRepository extends CrudRepository<PublicProfileFollowers, UUID> {
    Optional<PublicProfileFollowers> findByPublicProfileIdAndFollowerId(UUID publicProfileId, int followerId);
}
