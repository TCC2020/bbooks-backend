package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserBooksRepository  extends CrudRepository<UserBooks, Long> {
    Set<UserBooks> findByProfileId(int profileId);
}
