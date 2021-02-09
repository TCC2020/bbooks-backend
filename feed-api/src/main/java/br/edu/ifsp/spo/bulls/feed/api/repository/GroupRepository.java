package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<GroupRead, UUID> {
    boolean existsByName(String name);

    Page<GroupRead> findByNameContaining(String searchTerm, Pageable pageable);

}
