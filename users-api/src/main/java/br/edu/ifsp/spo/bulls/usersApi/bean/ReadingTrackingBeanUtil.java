package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.User;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.UserTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.ReadingTrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReadingTrackingBeanUtil {

    private Logger logger = LoggerFactory.getLogger(ReadingTrackingBeanUtil.class);
    @Autowired
    ReadingTrackingRepository repository;


//    public ReadingTracking toDomain(ReadingTrackingTO readingTrackingTO) {
//        ReadingTracking readingTracking = new ReadingTracking();
//
//        try{
//            BeanUtils.copyProperties(readingTrackingTO, readingTracking);
//        }catch(Exception e) {
//            e.printStackTrace();
//            logger.error("Error while converting UserTO to User: " +  e);
//        }
//        return readingTracking;
//    }
//
//    public ReadingTrackingTO toDTO(ReadingTracking readingTracking) {
//        ReadingTrackingTO readingTrackingTO = new ReadingTrackingTO();
//
//        try{
//            BeanUtils.copyProperties(readingTracking, readingTrackingTO);
//        }catch(Exception e) {
//            e.printStackTrace();
//            logger.error("Error while converting UserTO to User: " +  e);
//        }
//        return readingTrackingTO;
//    }
//
//
//    public HashSet<ReadingTrackingTO> toDTO(HashSet<ReadingTracking> readingTrackings){
//        HashSet<ReadingTrackingTO> readingTrackingTOS = new HashSet<ReadingTrackingTO>();
//        for (ReadingTracking readingTracking: readingTrackings ) {
//            readingTrackingTOS.add(toDTO(readingTracking));
//        }
//        return readingTrackingTOS;
//    }
}
