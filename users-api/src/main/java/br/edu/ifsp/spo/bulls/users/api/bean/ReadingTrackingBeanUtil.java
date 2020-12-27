package br.edu.ifsp.spo.bulls.users.api.bean;

import br.edu.ifsp.spo.bulls.users.api.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.users.api.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.users.api.repository.TrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadingTrackingBeanUtil {

    private Logger logger = LoggerFactory.getLogger(ReadingTrackingBeanUtil.class);

    @Autowired
    private TrackingRepository trackingRepository;


    public ReadingTracking toDomain(ReadingTrackingTO readingTrackingTO) {
        ReadingTracking readingTracking = new ReadingTracking();

        try{
            BeanUtils.copyProperties(readingTrackingTO, readingTracking);
            readingTracking.setTrackingGroup(trackingRepository.findById(readingTrackingTO.getTrackingUpId()).get());
        }catch(Exception e) {
            logger.error("Error while converting TrackingTO to Tracking: " +  e);
        }
        return readingTracking;
    }

    public ReadingTrackingTO toDTO(ReadingTracking readingTracking) {
        ReadingTrackingTO readingTrackingTO = new ReadingTrackingTO();

        try{
            BeanUtils.copyProperties(readingTracking, readingTrackingTO);
            readingTrackingTO.setTrackingUpId(readingTracking.getTrackingGroup().getId());
        }catch(Exception e) {
            logger.error("Error while converting Tracking to TrackingTO: " +  e);
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
