package bh.app.chronomicon.service;


import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.ShiftEntity;
import bh.app.chronomicon.model.enums.Shift;
import bh.app.chronomicon.repository.ShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ShiftService {
    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);

    @Autowired
    ShiftRepository shiftRepository;

    public ShiftEntity createShift(Shift shift, LocalDate date){
        ShiftEntity shiftEntity = new ShiftEntity ();
        shiftEntity.setShift (shift);
        shiftEntity.setDate (date);
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
