package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.PostBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.PostPrivacy;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.Optional;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostRepository mockPostRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostBeanUtil postBeanUtil;

    private Post post;
    private Post comment;
    private PostTO postTO;
    private List<PostTO> comments;
    private List<PostTO> postByProfile;
    private Page<PostTO> postPage;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(UUID.randomUUID());
        post.setProfileId(1);
        post.setCreationDate(LocalDateTime.now());
        post.setDescription("post");
        post.setTipoPost(TypePost.post);
        post.setPrivacy(PostPrivacy.friends_only);

        comment = new Post();
        comment.setId(UUID.randomUUID());
        comment.setProfileId(3);
        comment.setCreationDate(LocalDateTime.now().plusDays(1));
        comment.setDescription("comment");
        comment.setTipoPost(TypePost.comentario);
        comment.setPrivacy(PostPrivacy.friends_only);
        comment.setUpperPostId(post.getId());

        comments = new ArrayList<>();
        PostTO comentario = postBeanUtil.toDto(comment);
        comments.add(comentario);

        postTO = new PostTO();
        PostTO postagem = postBeanUtil.toDto(post);
        postTO = postagem;
        postTO.setComments(comments);

        postByProfile = new ArrayList<>();
        postByProfile.add(postTO);

        postPage = new Page<PostTO>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super PostTO, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<PostTO> getContent() {
                return postByProfile;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @NotNull
            @Override
            public Iterator<PostTO> iterator() {
                return null;
            }
        };


    }

    @Test
    void create() {
        Mockito.when(mockPostRepository.save(post)).thenReturn(post);

        Post result = postService.create(post);

        assertEquals(post, result);
    }

    @Test
    void update() {
        Mockito.when(mockPostRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        Mockito.when(mockPostRepository.save(post)).thenReturn(post);

        Post result = postService.update(post, post.getId());

        assertEquals(post, result);
    }

    @Test
    void updatePostNotFound() {

        Mockito.when(mockPostRepository.findById(post.getId())).thenThrow(new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));

        assertThrows(ResourceNotFoundException.class, () -> postService.update(post, post.getId()));

    }

    @Test
    void get() {
        Mockito.when(mockPostRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        Mockito.when(mockPostRepository.findByUpperPostId(comment.getUpperPostId())).thenReturn(comments);

        PostTO result = postService.get(post.getId());

        assertEquals(postTO, result);
    }

    @Test
    void getPostNotFound() {
        Mockito.when(mockPostRepository.findById(post.getId())).thenThrow(new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));

        assertThrows(ResourceNotFoundException.class, () -> postService.get(post.getId()));
    }

    @Test
    void getByProfile() {
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
    void delete() {
        Mockito.doNothing().when(mockPostRepository).deleteById(post.getId());

        postService.delete(post.getId());

        Mockito.verify(mockPostRepository).deleteById(post.getId());

    }
}