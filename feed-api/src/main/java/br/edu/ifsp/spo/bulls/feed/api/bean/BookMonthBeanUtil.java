package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.BookMonthTO;
import br.edu.ifsp.spo.bulls.feed.api.domain.BookMonth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMonthBeanUtil {

    private Logger logger = LoggerFactory.getLogger(BookMonthBeanUtil.class);

    public BookMonth toDomain(BookMonthTO monthTO){
        BookMonth bookMonth = new BookMonth();
        try{
            BeanUtils.copyProperties(monthTO, bookMonth);
        }catch(Exception e) {
            logger.error("Error while converting BookMonthTO to BookMonth: " +  e);
        }
        return bookMonth;
    }

    public BookMonthTO toDto(BookMonth bookMonth){
        BookMonthTO bookMonthTO = new BookMonthTO();
        try{
            BeanUtils.copyProperties(bookMonth, bookMonthTO);
        }catch(Exception e) {
            logger.error("Error while converting BookMonth to BookMonthTO: " +  e);
        }
        return bookMonthTO;
    }

    public List<BookMonthTO> toDto(List<BookMonth> bookMonth){
        return bookMonth.stream().map(this::toDto).collect(Collectors.toList());
    }
}
