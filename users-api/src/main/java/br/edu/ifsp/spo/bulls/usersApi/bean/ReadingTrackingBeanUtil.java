package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserBooksTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.UserBooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Component
public class ReadingTrackingBeanUtil {

    private Logger logger = LoggerFactory.getLogger(ReadingTrackingBeanUtil.class);
    @Autowired
    ReadingTrackingRepository repository;

    @Autowired
    UserBooksBeanUtil userBooksBeanUtil;

    @Autowired
    UserBooksRepository userBooksRepository;


    public ReadingTracking toDomain(ReadingTrackingTO readingTrackingTO) {
        ReadingTracking readingTracking = new ReadingTracking();

        try{
            BeanUtils.copyProperties(readingTrackingTO, readingTracking);
        }catch(Exception e) {
            logger.error("Error while converting UserTO to User: " +  e);
        }
        return readingTracking;
    }

    public ReadingTrackingTO toDTO(ReadingTracking readingTracking) {
        ReadingTrackingTO readingTrackingTO = new ReadingTrackingTO();

        try{
            BeanUtils.copyProperties(readingTracking, readingTrackingTO);
        }catch(Exception e) {
            logger.error("Error while converting UserTO to User: " +  e);
        }
        return readingTrackingTO;
    }


    public List<ReadingTrackingTO> toDTO(List<ReadingTracking> readingTrackings){
        List<ReadingTrackingTO> readingTrackingTOS = new ArrayList<>();
        for (ReadingTracking readingTracking: readingTrackings ) {
            readingTrackingTOS.add(toDTO(readingTracking));
        }
        return readingTrackingTOS;
    }
}
