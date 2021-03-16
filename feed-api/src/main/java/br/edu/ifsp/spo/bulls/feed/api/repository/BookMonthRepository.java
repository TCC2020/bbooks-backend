package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.feed.api.domain.BookMonth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookMonthRepository extends CrudRepository<BookMonth, UUID>  {

    List<BookMonth> findByGroupId(UUID groupId);

    void deleteByGroupId(UUID groupId);
}
