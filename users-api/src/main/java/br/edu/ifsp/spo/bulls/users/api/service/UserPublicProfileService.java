package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileCreateTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileUpdateTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.users.api.bean.PublicProfileBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.domain.PublicProfile;
import br.edu.ifsp.spo.bulls.users.api.domain.PublicProfileFollowers;
import br.edu.ifsp.spo.bulls.users.api.domain.User;
import br.edu.ifsp.spo.bulls.users.api.repository.FollowersRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserPublicProfileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserPublicProfileService {
    @Autowired
    private UserPublicProfileRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserPublicProfileRepository publicProfileRepository;

    @Autowired
    private FollowersRepository followersRepository;

    @Autowired
    private PublicProfileBeanUtil util;

    public UserPublicProfileTO create(String token, UserPublicProfileCreateTO dto) {
        User user = userService.getByToken(StringUtils.removeStart(token, "Bearer"));
        if(repository.findByUserId(user.getId()).isPresent())
            throw new ResourceConflictException(CodeException.UPF002);
        PublicProfile publicProfile = new PublicProfile();
        publicProfile.setDescription(dto.getDescription());
        publicProfile.setName(dto.getName());
        publicProfile.setUser(user);
        return util.toDto(repository.save(publicProfile));
    }

    public List<UserPublicProfileTO> search(String searchName) {
        return util.toDtoList(repository.findByNameContaining(searchName));
    }

    public HttpStatus follow(String token, UUID publicProfileId) {
        publicProfileRepository.findById(publicProfileId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UPF001));

        ProfileTO profile = profileService.getByToken(StringUtils.removeStart(token, "Bearer"));
        if(followersRepository.findByPublicProfileIdAndFollowerId(publicProfileId, profile.getId()).isPresent())
            return HttpStatus.NOT_MODIFIED;

        PublicProfileFollowers follower = new PublicProfileFollowers(publicProfileId, profile.getId());
        followersRepository.save(follower);
        return HttpStatus.CREATED;
    }

    public void unfollow(String token, UUID publicProfileId) {
        PublicProfile publicProfile = publicProfileRepository.findById(publicProfileId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UPF001));

        ProfileTO profile = profileService.getByToken(StringUtils.removeStart(token, "Bearer"));
        followersRepository.findByPublicProfileIdAndFollowerId(publicProfileId, profile.getId())
                .ifPresent(f -> followersRepository.deleteById(f.getId()));
    }

    public void delete(String token, UUID publicProfileId) {
        PublicProfile publicProfile = publicProfileRepository.findById(publicProfileId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UPF001));

        User user = userService.getByToken(StringUtils.removeStart(token, "Bearer"));
        if(publicProfile.getUser().getId().equals(user.getId()))
            publicProfileRepository.deleteById(publicProfileId);
    }

    public UserPublicProfileTO update(String token, UserPublicProfileUpdateTO dto) {
        PublicProfile publicProfile = publicProfileRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.UPF001));

        User user = userService.getByToken(StringUtils.removeStart(token, "Bearer"));
        if(!publicProfile.getUser().getId().equals(user.getId()))
            throw new ResourceUnauthorizedException(CodeException.UPF003);
        publicProfile.setDescription(dto.getDescription());
        publicProfile.setName(dto.getName());
        return util.toDto(repository.save(publicProfile));
    }

    public UserPublicProfileTO getById(UUID id) {
        return util.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.UPF001)));
    }

    public UserPublicProfileTO getByUserId(UUID id) {
        return util.toDto(repository.findByUserId(id).orElse(null));

    }
}
