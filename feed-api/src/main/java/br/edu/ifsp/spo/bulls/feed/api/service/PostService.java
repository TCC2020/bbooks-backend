package br.edu.ifsp.spo.bulls.feed.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.bean.PostBeanUtil;
import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.enums.TypePost;
import br.edu.ifsp.spo.bulls.feed.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.feed.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private PostBeanUtil postBeanUtil;

    @Autowired
    private UserCommonFeign feign;

    public Post create(PostTO postTO) {
        Post post = postBeanUtil.toDomain(postTO);
        return repository.save(post);
    }

    public Post update(Post post, UUID idPost) {
        return repository.findById(idPost).map( post1 -> {
                    post1 = post;
                    post1.setId(idPost);
                    return repository.save(post1);
                })
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));
    }

    public PostTO get(UUID idPost) {
        PostTO postTO = postBeanUtil.toDto(repository.findById(idPost)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001)));

        postTO.setComments(repository.findByUpperPostIdQuery(idPost));

        return postTO;
    }

    public Page<PostTO> getByProfile(int profileId, int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.Direction.ASC,
                "id");

        return repository.findByProfileId(profileId, TypePost.post, pageRequest);
    }

    public void delete(UUID idPost) {

        repository.deleteById(idPost);
    }
    public List<PostTO> getCommentList(UUID idPost, int page, int pageSize) {
        return postBeanUtil.toDtoList(repository.findByUpperPostId(idPost));
    }

    public Page<PostTO> getComment(UUID idPost, int page, int pageSize) {

        Pageable pageRequest = PageRequest.of(
                page,
                pageSize,
                Sort.Direction.ASC,
                "id");
        return repository.findByUpperPostIdQuery(idPost, pageRequest);
    }

    public HttpStatus setImage(String token, UUID postId, String url) {
        Post post = repository.findById(postId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.PT001.getText(), CodeException.PT001));

        ProfileTO profileTO = feign.getProfileByToken(token);
        if(profileTO.getId() != post.getProfileId())
            throw new ResourceConflictException(CodeException.PT002);
        post.setImage(url);
        repository.save(post);
        return HttpStatus.CREATED;
    }
}
