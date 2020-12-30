package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.users.api.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.service.ProfileService;
import br.edu.ifsp.spo.bulls.users.api.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserBooksBeanUtil {

    private Logger logger = LoggerFactory.getLogger(UserBooksBeanUtil.class);

    @Autowired
    private TagService tagService;
    @Autowired
    private TagBeanUtil tagBeanUtil;

    @Autowired
    private ProfileRepository profileRepository;

    public UserBooksTO toDto(UserBooks userBooks) {
        UserBooksTO userBooksTO = new UserBooksTO();
        try{
            BeanUtils.copyProperties(userBooks, userBooksTO);
        }catch(Exception e) {
            logger.error("Error while converting UserBooks to UserBooksTO: " +  e);
        }
        userBooksTO.setStatus(userBooks.getStatus());
        userBooksTO.setProfileId(userBooks.getProfile().getId());
        userBooksTO.setTags(tagBeanUtil.toDtoList(tagService.getByIdBook(userBooks.getId())));
        userBooksTO.setIdBook(userBooks.getBook() != null? userBooks.getBook().getId(): 0);
        return userBooksTO;
    }

    public UserBookUpdateStatusTO toDTOUpdate(UserBooks userBooks) {
        UserBookUpdateStatusTO userBooksTO = new UserBookUpdateStatusTO();
        try{
            BeanUtils.copyProperties(userBooks, userBooksTO);
        }catch(Exception e) {
            logger.error("Error while converting UserBooks to UserBooksTO: " +  e);
        }
        userBooksTO.setStatus(userBooks.getStatus().name());
        return userBooksTO;
    }

    public UserBooks toDomain(UserBooksTO dto) {
        UserBooks userBooks = new UserBooks();
        try{
            BeanUtils.copyProperties(dto, userBooks);
        }catch(Exception e) {
            logger.error("Error while converting UserBooksTO to UserBooks: " +  e);
        }
        userBooks.setStatus(dto.getStatus());
        userBooks.setProfile(profileRepository.findById(dto.getProfileId())
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001)));
        return userBooks;
    }

    public Set<UserBooksTO> toDtoSet(Set<UserBooks> userBooks) {
        Set<UserBooksTO> userBooksTOSet = new HashSet<>();

        for (UserBooks book: userBooks ) {
            userBooksTOSet.add(this.toDto(book));
        }
        return userBooksTOSet;
    }

}
