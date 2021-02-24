package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.BookAdTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.domain.BookAd;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.BookAdRepository;
import br.edu.ifsp.spo.bulls.competition.api.util.BookAdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookAdService {
    @Autowired
    private BookAdRepository repository;

    @Autowired
    private BookAdUtil utils;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private UserCommonFeign feign;

    public BookAdTO create(String token, BookAdTO dto) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        UserTO user = feign.getUserInfo(tokenValue);
        if(user == null)
            throw new ResourceUnauthorizedException(CodeException.US001.getText(), CodeException.US001);
        dto.setUserId(user.getId());
        return utils.toDto(repository.save(utils.toDomain(dto)));
    }

    public BookAdTO update(String token, BookAdTO dto) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        UserTO user = feign.getUserInfo(tokenValue);
        if(user == null || !user.getId().equals(dto.getUserId()))
            throw new ResourceUnauthorizedException(CodeException.US001.getText(), CodeException.US001);

        return utils.toDto(repository.save(utils.toDomain(dto)));
    }


    public List<BookAdTO> getAds() {
        return utils.toDtoList(repository.findAll()
                .stream().parallel().filter(bookAd -> bookAd.getIsOpen()).collect(Collectors.toList()));
    }

    public List<BookAdTO> getAdsByUser(UUID userId) {
        return utils.toDtoList(repository.findByUserId(userId));
    }

    public void deleteById(String token, UUID id) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        UserTO user = feign.getUserInfo(tokenValue);
        BookAd ad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.BAD001.getText(), CodeException.BAD001));
        if(exchangeService.hasExchangesById(id))
            throw new ResourceConflictException(CodeException.BAD003);
        if(ad.getUserId().equals(user.getId())) {
            repository.deleteById(id);
            return;
        }
        throw new ResourceUnauthorizedException(CodeException.BAD002.getText(), CodeException.BAD002);
    }

    public BookAdTO getAdById(UUID id) {
        return utils.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.BAD001.getText(), CodeException.BAD001)));
    }

    public HttpStatus setImage(UUID bookAdId, String url, String token) {
        BookAd ad = repository.findById(bookAdId).orElseThrow(() -> new ResourceNotFoundException(CodeException.BAD001));
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        UserTO user = feign.getUserInfo(tokenValue);
        if(user == null || !user.getId().equals(ad.getUserId()))
            throw new ResourceUnauthorizedException(CodeException.US001.getText(), CodeException.US001);
        ad.getImages().add(url);
        repository.save(ad);
        return HttpStatus.CREATED;
    }
}
