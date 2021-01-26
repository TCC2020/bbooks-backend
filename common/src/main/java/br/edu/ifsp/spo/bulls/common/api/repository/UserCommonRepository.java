package br.edu.ifsp.spo.bulls.common.api.repository;

import br.edu.ifsp.spo.bulls.common.api.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//ESSA CLASSE SÓ PODE TER CHAMADAS RELACIONADA A SECURITY

@Repository
public interface UserCommonRepository extends CrudRepository<User, UUID> {
    Optional<User> findByToken(String token);

}
