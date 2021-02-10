package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.BookAdTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceUnauthorizedException;
import br.edu.ifsp.spo.bulls.competition.api.domain.BookAd;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.BookAdRepository;
import br.edu.ifsp.spo.bulls.competition.api.util.BookAdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookAdService {
    @Autowired
    private BookAdRepository repository;

    @Autowired
    private BookAdUtil utils;

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

    public List<BookAdTO> getAds() {
        return utils.toDtoList(repository.findAll());
    }

    public void deleteAd(String token, UUID id){
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        UserTO user = feign.getUserInfo(tokenValue);
        BookAd ad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.BAD001.getText(), CodeException.BAD001));
        if(ad.getUserId().equals(user.getId()))
            repository.deleteById(id);
        new ResourceUnauthorizedException(CodeException.BAD002.getText(), CodeException.BAD002);
    }

    public void deleteById(String token, UUID id) {
        String tokenValue = StringUtils.removeStart(token, "Bearer").trim();
        UserTO user = feign.getUserInfo(tokenValue);
        BookAd ad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CodeException.BAD001.getText(), CodeException.BAD001));
        if(ad.getUserId().equals(user.getId())) {
            repository.deleteById(id);
        }
    }
}
