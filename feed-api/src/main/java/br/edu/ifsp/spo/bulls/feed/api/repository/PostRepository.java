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
    List<PostTO> findByUpperPostIdQuery(@Param("upperPostId") UUID upperPostId);

    List<Post> findByUpperPostId(UUID upperPostId);

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.feed.api.dto.PostTO(" +
                    "p.id, p.profileId, p.description, p.creationDate, p.image, p.tipoPost, p.privacy) " +
                    "FROM Post p " +
                    "WHERE p.upperPostId = :upperPostId " +
                    "ORDER BY p.creationDate DESC"
    )
    Page<PostTO> findByUpperPostIdQuery(@Param("upperPostId") UUID upperPostId, Pageable pageable);

    @Query(value =
            "SELECT DISTINCT(p.*) FROM POST p, FRIENDSHIPS f " +
                    "WHERE (p.profile_id = f.profile1 AND f.profile2 = :id OR p.profile_id = f.profile2 AND f.profile1 = :id) AND f.status = 'added' AND p.group_id IS NULL " +
                    "OR (p.group_id IN (SELECT gro.group_id FROM profiles prof, group_members gro WHERE prof.user_id = gro.user_id AND prof.id = :id)) " +
                    "AND p.tipo_post = 'post' " +
                    "ORDER BY p.creation_date DESC", nativeQuery = true)
    List<Post> findFeedByRequesterId(@Param("id") int profileId);

    @Query(value =
            "SELECT new br.edu.ifsp.spo.bulls.feed.api.dto.PostTO"+
                    "(p.id, p.profileId, p.description, p.creationDate, p.image, p.tipoPost, p.privacy)" +
                    " FROM Post p WHERE p.privacy = 'public_all' AND p.profileId = :id AND p.tipoPost = 'post'")
    Page<PostTO> findFeedByRequesterIdPublic(@Param("id") int profileId, Pageable pageable);

}
