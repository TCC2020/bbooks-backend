package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.common.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface UserBooksRepository  extends CrudRepository<UserBooks, Long> {
    Set<UserBooks> findByProfile(Profile profile);

    @Query(value = "SELECT * FROM user_books WHERE profile_id =:profileId AND finish_date is not null ORDER BY finish_date ASC", nativeQuery = true)
    Set<UserBooks> findByProfileOrderByFinishDateDesc(@Param("profileId") int profileId);
}
