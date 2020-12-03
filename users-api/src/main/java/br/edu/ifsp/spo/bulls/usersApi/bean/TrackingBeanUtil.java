package br.edu.ifsp.spo.bulls.usersApi.bean;

import br.edu.ifsp.spo.bulls.usersApi.domain.ReadingTracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.Tracking;
import br.edu.ifsp.spo.bulls.usersApi.domain.UserBooks;
import br.edu.ifsp.spo.bulls.usersApi.dto.ReadingTrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.dto.TrackingTO;
import br.edu.ifsp.spo.bulls.usersApi.repository.TrackingRepository;
import br.edu.ifsp.spo.bulls.usersApi.repository.UserBooksRepository;
import br.edu.ifsp.spo.bulls.usersApi.service.ReadingTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackingBeanUtil {
    private Logger logger = LoggerFactory.getLogger(ReadingTrackingBeanUtil.class);

    @Autowired
    private ReadingTrackingBeanUtil readingTrackingBeanUtil;

    @Autowired
    private ReadingTrackingService readingTrackingService;

    @Autowired
    private UserBooksRepository userBooksRepository;

    public TrackingTO toDTO(Tracking tracking) {
        TrackingTO trackingTO = new TrackingTO();

        try{
            BeanUtils.copyProperties(tracking, trackingTO);
            trackingTO.setUserBookId(tracking.getUserBook().getId());
            List<ReadingTrackingTO> lista = readingTrackingBeanUtil.toDTO(readingTrackingService.getByTrackingGroup(trackingTO.getId()));
            trackingTO.setTrackings(lista);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("Error while converting Tracking to TrackingTO: " +  e);
        }
        return trackingTO;
    }

    public Tracking toDomain(TrackingTO trackingTO) {
        Tracking tracking = new Tracking();

        try{
            BeanUtils.copyProperties(trackingTO, tracking);
            UserBooks userBooks = userBooksRepository.findById(trackingTO.getUserBookId()).get();
            tracking.setUserBook(userBooks);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("Error while converting TrackingTO to Tracking: " +  e);
        }
        return tracking;
    }

    public List<TrackingTO> toDTO(List<Tracking> trackings){
        List<TrackingTO> trackingTO = new ArrayList<>();
        for (Tracking tracking: trackings ) {
            trackingTO.add(toDTO(tracking));
        }
        return trackingTO;
    }

}
