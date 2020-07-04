package br.edu.ifsp.spo.bulls.usersApi.repository;

import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import org.springframework.data.repository.CrudRepository;

public interface UserBooksRepository  extends CrudRepository<UserBooks, Long> {
}
