package br.edu.ifsp.spo.bulls.feed.api.bean;

import br.edu.ifsp.spo.bulls.common.api.dto.BookMonthTO;
import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import br.edu.ifsp.spo.bulls.common.api.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.feed.api.domain.BookMonth;
import br.edu.ifsp.spo.bulls.feed.api.domain.GroupRead;
import br.edu.ifsp.spo.bulls.feed.api.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMonthBeanUtil {

    private Logger logger = LoggerFactory.getLogger(BookMonthBeanUtil.class);

    @Autowired
    private GroupRepository groupRepository;

    public BookMonth toDomain(BookMonthTO monthTO){
        BookMonth bookMonth = new BookMonth();
        try{
            BeanUtils.copyProperties(monthTO, bookMonth);

            GroupRead groupRead = groupRepository.findById(monthTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(CodeException.GR001.getText(), CodeException.GR001));
            bookMonth.setGroup(groupRead);
        }catch(Exception e) {
            logger.error("Error while converting BookMonthTO to BookMonth: " +  e);
        }
        return bookMonth;
    }

    public BookMonthTO toDto(BookMonth bookMonth){
        BookMonthTO bookMonthTO = new BookMonthTO();
        try{
            BeanUtils.copyProperties(bookMonth, bookMonthTO);
            bookMonthTO.setGroupId(bookMonth.getGroup().getId());
        }catch(Exception e) {
            logger.error("Error while converting BookMonth to BookMonthTO: " +  e);
        }
        return bookMonthTO;
    }

    public List<BookMonthTO> toDto(List<BookMonth> bookMonth){
        return bookMonth.stream().map(this::toDto).collect(Collectors.toList());
    }
}
