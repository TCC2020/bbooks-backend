package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.PostBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.bean.SurveyBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.Privacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import br.edu.ifsp.spo.bulls.feed.api.repository.ReactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class, classes = {PostService.class, SurveyBeanUtil.class, PostBeanUtil.class, })
public class PostServiceTest {

    @MockBean
    private UserCommonFeign feign;

    @MockBean
    private PostRepository mockPostRepository;

    @MockBean
    private GroupRepository mockGroupRepository;

    @MockBean
    private ReactionsRepository reactionsRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostBeanUtil postBeanUtil;

    private Post post;
    private Post comment;
    private PostTO postTO;
    private List<PostTO> comments;
    private GroupRead group;

    @BeforeEach
    void setUp() {
        group = new GroupRead();
        group.setId(UUID.randomUUID());


        post = new Post();
        post.setId(UUID.randomUUID());
        post.setProfileId(1);
        post.setCreationDate(LocalDateTime.now());
        post.setDescription("post");
        post.setTipoPost(TypePost.post);
        post.setPrivacy(Privacy.friends_only);
        post.setGroup(group);

        comment = new Post();
        comment.setId(UUID.randomUUID());
        comment.setProfileId(3);
        comment.setCreationDate(LocalDateTime.now().plusDays(1));
        comment.setDescription("comment");
        comment.setTipoPost(TypePost.comentario);
        comment.setPrivacy(Privacy.friends_only);
        comment.setUpperPostId(post.getId());

        comments = new ArrayList<>();
        PostTO comentario = postBeanUtil.toDto(comment);
        comments.add(comentario);

        postTO = new PostTO();
        PostTO postagem = postBeanUtil.toDto(post);
        postTO = postagem;
        postTO.setComments(comments);
        postTO.setGroupId(group.getId());

        Mockito.when(reactionsRepository.findById(UUID.randomUUID())).thenReturn(null);

    }

    @Test
    void create() {
        Mockito.when(mockPostRepository.save(post)).thenReturn(post);
        Mockito.when(mockGroupRepository.findById(post.getGroup().getId())).thenReturn(Optional.ofNullable(group));
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);

        Post result = postService.create(postTO);

        assertEquals(post, result);
    }

    @Test
    void shouldNotcreateWhenGroupNotFound() {
        Mockito.when(mockPostRepository.save(post)).thenReturn(post);
        Mockito.when(mockGroupRepository.findById(post.getGroup().getId())).thenReturn(Optional.ofNullable(null));

        assertThrows(ResourceNotFoundException.class, () ->  postService.create(postTO));
    }

    @Test
    void update() {
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);
        Mockito.when(mockPostRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        Mockito.when(mockPostRepository.save(post)).thenReturn(post);

        Post result = postService.update(post, post.getId());

        assertEquals(post, result);
    }

    @Test
    void updatePostNotFound() {
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);
        Mockito.when(mockPostRepository.findById(post.getId())).thenThrow(new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));

        assertThrows(ResourceNotFoundException.class, () -> postService.update(post, post.getId()));

    }

    @Test
    void get() {
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);
        Mockito.when(mockPostRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        Mockito.when(mockPostRepository.findByUpperPostIdQuery(comment.getUpperPostId())).thenReturn(comments);

        PostTO result = postService.get(post.getId());

        assertEquals(postTO, result);
    }

    @Test
    void getPostNotFound() {
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);
        Mockito.when(mockPostRepository.findById(post.getId())).thenThrow(new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));

        assertThrows(ResourceNotFoundException.class, () -> postService.get(post.getId()));
    }

    @Test
    void getByProfile() {
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);
        Page<PostTO> postPage = null;
        Pageable pageRequest = PageRequest.of(
                0,
                1,
                Sort.Direction.ASC,
                "id");
        Mockito.when(mockPostRepository.findByProfileId(post.getProfileId(), TypePost.post, pageRequest)).thenReturn(postPage);

        Page<PostTO> result = postService.getByProfile(post.getProfileId(), 0, 1);

        assertEquals(postPage, result);
    }

    @Test
    void getComment() {
        Page<PostTO> postPage = null;
        Pageable pageRequest = PageRequest.of(
                0,
                1,
                Sort.Direction.ASC,
                "id");
        Mockito.when(mockPostRepository.findByUpperPostIdQuery(post.getId(), pageRequest)).thenReturn(postPage);

        Page<PostTO> result = postService.getComment(post.getId(), 0, 1);

        assertEquals(postPage, result);
    }

    @Test
    void delete() {
        Mockito.when(feign.getUserByProfileId(1)).thenReturn(null);
        Mockito.when(feign.getUserByProfileId(3)).thenReturn(null);
        Mockito.doNothing().when(mockPostRepository).deleteById(post.getId());

        postService.delete(post.getId());

        Mockito.verify(mockPostRepository).deleteById(post.getId());

    }
}
