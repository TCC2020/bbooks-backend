package br.edu.ifsp.spo.bulls.feed.api.repository;

import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<Post, UUID> {

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.feed.api.dto.PostTO(  " +
                    "p.id, p.profileId, p.description, p.creationDate, p.image, p.tipoPost, p.privacy) " +
                    "FROM Post p " +
                    "WHERE p.profileId = :profileId AND p.tipoPost = :tipoPost " +
                    "ORDER BY p.creationDate DESC"
    )
    Page<PostTO> findByProfileId(@Param("profileId") int profileId,
                                   @Param("tipoPost") TypePost typePost,
                                   Pageable pageable);

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.feed.api.dto.PostTO(" +
                    "p.id, p.profileId, p.description, p.creationDate, p.image, p.tipoPost, p.privacy) " +
                    "FROM Post p " +
                    "WHERE p.upperPostId = :upperPostId " +
                    "ORDER BY p.creationDate DESC"
    )
    List<PostTO> findByUpperPostId (@Param("upperPostId") UUID upperPostId);

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.feed.api.dto.PostTO "+
                    "(p.id, p.profileId, p.description, p.creationDate, p.image, p.tipoPost, p.privacy)" +
                    "FROM FRIENDSHIPS f, POST p WHERE (f.profile2 = p.profile_id or f.profile1 = p.profile_id) AND p.profile_id != :id " +
                    "ORDER BY p.creationDate DESC")
    Page<PostTO> findFeedByRequesterId(@Param("id") int profileId, Pageable pageable);

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.feed.api.dto.PostTO "+
                    "(p.id, p.profileId, p.description, p.creationDate, p.image, p.tipoPost, p.privacy)" +
                    "FROM POST p WHERE p.privacy = 'public'")
    Page<PostTO> findFeedByRequesterIdPublic(int id, int profileId, Pageable pageable);
}
