package br.edu.ifsp.spo.bulls.users.api.repository;

import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksDataStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.userBooksData;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserBooksRepository  extends CrudRepository<UserBooks, Long> {
    Set<UserBooks> findByProfile(Profile profile);

    @Query(value = "SELECT * FROM user_books WHERE profile_id =:profileId AND finish_date is not null ORDER BY finish_date ASC", nativeQuery = true)
    Set<UserBooks> findByProfileOrderByFinishDateDesc(@Param("profileId") int profileId);

    List<UserBooks> findByIdBookGoogle(String googleBook);
    List<UserBooks> findByBook(Book book);
}
