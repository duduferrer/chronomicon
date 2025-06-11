package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CreateShiftEventDTO;
import bh.app.chronomicon.dto.ShiftEventDTO;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import bh.app.chronomicon.model.entities.ShiftEventsEntity;
import bh.app.chronomicon.model.enums.EventType;
import bh.app.chronomicon.repository.ShiftEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class ShiftEventsService {
    private static final Logger log = LoggerFactory.getLogger(ShiftEventsService.class);


    @Autowired
    private ShiftEventsRepository shiftEventsRepository;
    @Autowired
    private ServiceShiftService serviceShiftService;

    private ShiftEventDTO createShiftEvent(int shiftID, EventType type, String details, Timestamp start, boolean isOngoing){
        Timestamp end = isOngoing ? null : start;
        ServiceShiftEntity serviceShiftEntity = serviceShiftService.getServiceShiftByID (shiftID);
        ShiftEventsEntity shiftEventsEntity= new ShiftEventsEntity(serviceShiftEntity, type, details, start, end);
        try{
            ShiftEventsEntity savedEvent = shiftEventsRepository.save (shiftEventsEntity);
            log.info ("EVENTO {} INCLUIDO COM SUCESSO", savedEvent.getId ());
            return new ShiftEventDTO (savedEvent.getId (), savedEvent.getType (), savedEvent.getDetails (), savedEvent.getStart (), savedEvent.getEnd ());
        } catch (RuntimeException e) {
            log.error ("ERRO AO INCLUIR EVENTO {}. ERRO: {}", shiftEventsEntity.getDetails (), e.toString ());
            throw new ServerException("ERRO AO INCLUIR EVENTO: "+shiftEventsEntity.getDetails());
        }
    }

}
