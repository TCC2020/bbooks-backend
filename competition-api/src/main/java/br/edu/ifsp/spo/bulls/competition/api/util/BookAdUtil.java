package br.edu.ifsp.spo.bulls.competition.api.util;

import br.edu.ifsp.spo.bulls.common.api.dto.AdReviewTO;
import br.edu.ifsp.spo.bulls.common.api.dto.BookAdTO;
import br.edu.ifsp.spo.bulls.competition.api.domain.AdReview;
import br.edu.ifsp.spo.bulls.competition.api.domain.BookAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookAdUtil {
    private Logger logger = LoggerFactory.getLogger(BookAdUtil.class);

    public BookAd toDomain(BookAdTO dto) {
        BookAd ad = new BookAd();
        AdReview adReview = new AdReview();
        try {
            BeanUtils.copyProperties(dto, ad);
            if(dto.getReview() != null){
                adReview.setId(dto.getReview().getId());
                adReview.setDescription(dto.getDescription());
                ad.setReview(adReview);
            }
        } catch (Exception ex) {
            logger.error("Error while converting BookAd: " + ex);
        }
        return ad;
    }

    public List<BookAdTO> toDtoList(List<BookAd> domains) {
        return domains.parallelStream().map(this::toDto).collect(Collectors.toList());
    }

    public BookAdTO toDto(BookAd domain) {
        BookAdTO dto = new BookAdTO();
        AdReviewTO adReview = new AdReviewTO();
        try {
            BeanUtils.copyProperties(domain, dto);
            if(domain.getReview() != null){
                adReview.setId(domain.getReview().getId());
                adReview.setDescription(domain.getDescription());
                dto.setReview(adReview);
            }
        } catch (Exception ex) {
            logger.error("Error while converting BookAd: " + ex);
        }
        return dto;
    }
}
