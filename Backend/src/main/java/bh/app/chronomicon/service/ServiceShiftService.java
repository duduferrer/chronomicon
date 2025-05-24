package bh.app.chronomicon.service;


import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.repository.ServiceShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ServiceShiftService {
    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);

    @Autowired
    ServiceShiftRepository shiftRepository;

    public ServiceShiftEntity createShift(ShiftType shift, LocalDate date){
        ServiceShiftEntity shiftEntity = new ServiceShiftEntity ();
        shiftEntity.setShift (shift);
        shiftEntity.setDate (date);
        shiftEntity.setActive (true);
        try {
            shiftRepository.save (shiftEntity);
            log.info ("TURNO CRIADO: {}, {}", shift, date.toString ());
        } catch (RuntimeException e) {
            log.error ("NÃO FOI POSSÍVEL CRIAR TURNO. {}, {}", shift, date.toString ());
            throw new ServerException ("NÃO FOI POSSÍVEL CRIAR TURNO. "+ shift + ", "+ date.toString ());
        }
        return shiftEntity;
    }
}
