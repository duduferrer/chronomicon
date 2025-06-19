package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CloseShiftEventDTO;
import bh.app.chronomicon.dto.ShiftEventDTO;
import bh.app.chronomicon.dto.UpdateShiftEventDTO;
import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.NotActiveEventException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.mapper.ShiftEventMapper;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import bh.app.chronomicon.model.entities.ShiftEventsEntity;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.EventType;
import bh.app.chronomicon.repository.ShiftEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ShiftEventsService {
    private static final Logger log = LoggerFactory.getLogger(ShiftEventsService.class);


    @Autowired
    private ShiftEventsRepository shiftEventsRepository;
    @Autowired
    private ServiceShiftService serviceShiftService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShiftEventMapper shiftEventMapper;

    public ShiftEventDTO createShiftEvent(int shiftID, EventType type, String details, Timestamp start, boolean isOngoing, String userLpna){
        Timestamp end = isOngoing ? null : start;
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        UserEntity userEntity = userService.findUser(userLpna);
        ServiceShiftEntity serviceShiftEntity = serviceShiftService.getServiceShiftByID (shiftID);
        ShiftEventsEntity shiftEventsEntity= new ShiftEventsEntity(serviceShiftEntity, type, details, start, end, now, now, userEntity, userEntity);
        try{
            ShiftEventsEntity savedEvent = shiftEventsRepository.save (shiftEventsEntity);
            log.info ("EVENTO {} INCLUIDO COM SUCESSO", savedEvent.getId ());
            return new ShiftEventDTO (savedEvent.getId (), savedEvent.getType (), savedEvent.getDetails (), savedEvent.getStart (), savedEvent.getEnd ());
        } catch (RuntimeException e) {
            log.error ("ERRO AO INCLUIR EVENTO {}. ERRO: {}", shiftEventsEntity.getDetails (), e.toString ());
            throw new ServerException("ERRO AO INCLUIR EVENTO: "+shiftEventsEntity.getDetails());
        }
    }

    public ShiftEventDTO endOpenedShiftEvent(String details, String eventID, Timestamp end, String userLpna){
        ShiftEventsEntity shiftEventsEntity = getShiftEventByID (eventID);
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        UserEntity userEntity = userService.findUser(userLpna);
        if(shiftEventsEntity.getEnd () == null){
            CloseShiftEventDTO closeShiftEventDTO = new CloseShiftEventDTO (details, end, userEntity, now);
            shiftEventMapper.closeShiftEventFromDTO (closeShiftEventDTO, shiftEventsEntity);
            try{
                ShiftEventsEntity savedEvent = shiftEventsRepository.save (shiftEventsEntity);
                log.info ("EVENTO {} INCLUIDO COM SUCESSO", savedEvent.getId ());
                return new ShiftEventDTO (savedEvent.getId (), savedEvent.getType (), savedEvent.getDetails (), savedEvent.getStart (), savedEvent.getEnd ());
            } catch (RuntimeException e) {
                log.error ("ERRO AO INCLUIR EVENTO {}. ERRO: {}", shiftEventsEntity.getDetails (), e.toString ());
                throw new ServerException("ERRO AO INCLUIR EVENTO: "+shiftEventsEntity.getDetails());
            }
        }else{
            log.warn ("Evento: {} já consta com horário de fechamento({}).",shiftEventsEntity.getId (), shiftEventsEntity.getEnd ());
            throw new NotActiveEventException ("Evento: "+eventID+" já possui horário de encerramento.");
        }

    }

    public ShiftEventDTO updateShiftEvent(String eventID, EventType type, String details, Timestamp start, Timestamp end, boolean isOngoing, String userLpna){
        ShiftEventsEntity shiftEventsEntity = getShiftEventByID (eventID);
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        UserEntity userEntity = userService.findUser(userLpna);
        UpdateShiftEventDTO updateShiftEventDTO = new UpdateShiftEventDTO (type, details, start, end, now, userEntity);
        shiftEventMapper.updateShiftEventFromDTO (updateShiftEventDTO, shiftEventsEntity);
        try{
            ShiftEventsEntity savedEvent = shiftEventsRepository.save (shiftEventsEntity);
            log.info ("EVENTO {} INCLUIDO COM SUCESSO", savedEvent.getId ());
            return new ShiftEventDTO (savedEvent.getId (), savedEvent.getType (), savedEvent.getDetails (), savedEvent.getStart (), savedEvent.getEnd ());
        } catch (RuntimeException e) {
            log.error ("ERRO AO INCLUIR EVENTO {}. ERRO: {}", shiftEventsEntity.getDetails (), e.toString ());
            throw new ServerException("ERRO AO INCLUIR EVENTO: "+shiftEventsEntity.getDetails());
        }

    }

    private ShiftEventsEntity getShiftEventByID(String eventID){
        ShiftEventsEntity shiftEventsEntity = shiftEventsRepository.findById (eventID).orElseThrow (
                ()->{
                    log.warn ("Evento de turno não encontrado. ID: {}", eventID);
                    return new NotFoundException ("Evento de turno de ID: "+eventID+" não encontrado");
                }
        );
        log.info ("EVENTO DE TURNO {} ENCONTRADO", eventID);
        return shiftEventsEntity;
    }

}
