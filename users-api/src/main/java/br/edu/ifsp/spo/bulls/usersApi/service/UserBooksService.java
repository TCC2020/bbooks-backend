package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.usersApi.domain.Profile;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookCaseTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBookUpdateStatusTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserBooksService {
    @Autowired
    private UserBooksRepository repository;
    @Autowired
    private UserBooksBeanUtil util;
    @Autowired
    private TagService tagService;

    @Autowired
    private ProfileRepository profileRepository;

    public UserBooksTO save(UserBooksTO dto) {
        UserBooks userBooks = repository.save(util.toDomain(dto));
        for(Tag t : dto.getTags()){
            tagService.tagBook(t.getId(), userBooks.getId());
        }
        return util.toDto(userBooks);
    }

    public UserBooksTO updateStatus(UserBookUpdateStatusTO dto) {
        UserBooks userBooks = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado na estante"));
        userBooks.setStatus(UserBooks.Status.getByString(dto.getStatus()));
        if(userBooks.getStatus() == null)
            throw new ResourceConflictException("Status inválido");
        return util.toDto(repository.save(userBooks));
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public BookCaseTO getByProfileId(int profileId) {
        BookCaseTO bookCase = new BookCaseTO();
        Optional<Profile> profile = profileRepository.findById(profileId);
        Set<UserBooks> userBooks = repository.findByProfile(profile.get());
        bookCase.setProfileId(profileId);
        bookCase.setBooks(userBooks);
        return bookCase;
    }

    public UserBooksTO update(UserBooksTO dto) {

        if (dto.getTags().isEmpty()) {
            List<Tag> tagsRemove = tagService.getByIdBook(dto.getId());
            for(Tag tag: tagsRemove){
                tagService.untagBook(tag.getId(),dto.getId());
            }
            this.deleteById(dto.getId());
            return new UserBooksTO();
        }else{
            List<Tag> tagsRemove = tagService.getByIdBook(dto.getId());
            for(Tag tag: tagsRemove){
                    tagService.untagBook(tag.getId(),dto.getId());
            }
            return this.save(dto);
        }
    }
}
