package br.edu.ifsp.spo.bulls.users.api.service;

import br.edu.ifsp.spo.bulls.users.api.domain.Profile;
import br.edu.ifsp.spo.bulls.users.api.domain.Tag;
import br.edu.ifsp.spo.bulls.users.api.domain.UserBooks;
import br.edu.ifsp.spo.bulls.users.api.domain.Book;
import br.edu.ifsp.spo.bulls.users.api.dto.*;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.users.api.enums.Status;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.users.api.bean.UserBooksBeanUtil;
import br.edu.ifsp.spo.bulls.users.api.repository.BookRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ProfileRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.ReadingTargetRepository;
import br.edu.ifsp.spo.bulls.users.api.repository.UserBooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserBooksService {
    private Logger logger = LoggerFactory.getLogger(UserBooksService.class);


    @Autowired
    private UserBooksRepository repository;

    @Autowired
    private UserBooksBeanUtil util;

    @Autowired
    private TagService tagService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ReadingTargetRepository targetRepository;

    public UserBooksTO save(UserBooksTO dto) {
        UserBooks userBooks = convertToUserBooks(dto);
        verifyIfUserbookIsAlreadyOnDatabase(userBooks);
        userBooks = repository.save(userBooks);

        saveTags(dto, userBooks);

        return util.toDto(userBooks);
    }

    private void verifyIfUserbookIsAlreadyOnDatabase(UserBooks userBooks) {
        Set<UserBooks> userBooksList = repository.findByProfile(userBooks.getProfile());

        for(UserBooks userBooks1 : userBooksList){
            if(     (userBooks1.getBook()!= null && (userBooks1.getBook() == userBooks.getBook() ))
                ||  (userBooks1.getIdBookGoogle()!= null && (userBooks1.getIdBookGoogle().equals(userBooks.getIdBookGoogle())))
                ){
                throw new ResourceConflictException(CodeException.UB003.getText(), CodeException.UB003);
            }
        }
    }

    public UserBooksTO updateStatus(UserBookUpdateStatusTO dto) {
        UserBooks userBooks = repository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        userBooks.setStatus(Status.getByString(dto.getStatus()));
        if(userBooks.getStatus() == null)
            throw new ResourceConflictException(CodeException.UB002.getText(), CodeException.UB002);

        UserBooks userBooks1 = repository.save(userBooks);
        return util.toDto(userBooks1);
    }

    public void deleteById(Long id){
        targetRepository.deleteTargetRelationship(id);
        repository.deleteById(id);
    }

    public BookCaseTO getByProfileId(int profileId, boolean timeLine) {
        BookCaseTO bookCase = new BookCaseTO();
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));

        Set<UserBooks> userBooks;
        if(timeLine){
            userBooks = repository.findByProfileOrderByFinishDateDesc(profileId);
        }else{
            userBooks = repository.findByProfile(profile);
        }

        bookCase.setProfileId(profileId);
        Set<UserBooksTO> userBooksTO = util.toDtoSet(userBooks);
        bookCase.setBooks(userBooksTO);

        return bookCase;
    }

    public UserBooksTO getById(Long bookId) {
        UserBooks userBooks = repository.findById(bookId)
                .orElseThrow( () -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001));
        return util.toDto(userBooks);
    }

    public UserBooksTO update(UserBooksTO dto, Long id) {
        verifyIfRequestHasConflict(dto, id);
        removeTags(dto);
        UserBooks userBook = util.toDomain(verifyFinishDate(dto));
        saveTags(dto, userBook);

        UserBooks resultado = repository.findById(id).map(userBooks1 -> {
            userBooks1 = userBook;
            return repository.save(userBooks1);
        }).orElseThrow( () -> new ResourceNotFoundException(CodeException.UB001.getText(), CodeException.UB001 ));

        return util.toDto(resultado);
    }

    private void verifyIfRequestHasConflict(UserBooksTO dto, Long id) {
        if(!dto.getId().equals(id))
            throw new ResourceConflictException(CodeException.UB004.getText(), CodeException.UB004);

        if(dto.getStatus() == null)
            throw new ResourceConflictException(CodeException.UB002.getText(), CodeException.UB002);
    }

    private UserBooksTO verifyFinishDate(UserBooksTO dto) {
        if(dto.getFinishDate() != null
                && dto.getFinishDate().isBefore(LocalDateTime.now())
                && !dto.getStatus().equals(Status.LIDO))
            dto.setStatus(Status.LIDO);

        return dto;
    }

    private void removeTags(UserBooksTO dto) {
        List<Tag> tagsRemove = tagService.getByIdBook(dto.getId());
        for(Tag tag: tagsRemove){
            tagService.untagBook(tag.getId(),dto.getId());
        }
    }

    public UserBooks convertToUserBooks(UserBooksTO dto) {
        UserBooks userBooks = util.toDomain(dto);
        if(dto.getIdBook() != 0){
            userBooks.setBook(getBook(dto));
        }
        Profile profile = getProfile(dto);
        userBooks.setProfile(profile);
        return userBooks;
    }

    public Book getBook(UserBooksTO dto) {
        return bookRepository.findById(dto.getIdBook()).orElseThrow( () -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));
    }

    public Profile getProfile(UserBooksTO dto) {
        return profileRepository.findById(dto.getProfileId()).orElseThrow( () -> new ResourceNotFoundException(CodeException.PF001.getText(), CodeException.PF001));
    }

    private void saveTags(UserBooksTO dto, UserBooks userBooks) {
        for(TagTO t : dto.getTags()){
            tagService.tagBook(t.getId(), userBooks.getId());
        }
    }

    public UserBooksDataStatusTO getStatusData(String googleBook, int bookId) {
        List<UserBooks> data;

        if(bookId != 0){
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));
            data = repository.findByBook(book);
        }
        else{
            data = repository.findByIdBookGoogle(googleBook);
        }

        return this.mapToStatusData(data, googleBook, bookId);
    }

    public UserBooksDataStatusTO mapToStatusData(List<UserBooks> data, String googleBook, int bookId){
        UserBooksDataStatusTO dataStatusTO = new UserBooksDataStatusTO();

        dataStatusTO.setGoogleId(googleBook);
        dataStatusTO.setBookId(bookId);
        dataStatusTO.setEmprestado(data.stream().filter(x -> x.getStatus() == Status.EMPRESTADO).count());
        dataStatusTO.setInterrompido(data.stream().filter(x -> x.getStatus() == Status.INTERROMPIDO).count());
        dataStatusTO.setLendo(data.stream().filter(x -> x.getStatus() == Status.LENDO).count());
        dataStatusTO.setLido(data.stream().filter(x -> x.getStatus() == Status.LIDO).count());
        dataStatusTO.setQueroLer(data.stream().filter(x -> x.getStatus() == Status.QUERO_LER).count());
        dataStatusTO.setRelendo(data.stream().filter(x -> x.getStatus() == Status.RELENDO).count());

        return dataStatusTO;
    }
}
