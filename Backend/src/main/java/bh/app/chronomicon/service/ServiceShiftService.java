package bh.app.chronomicon.service;


import bh.app.chronomicon.dto.UpdateShiftDTO;
import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.ForbiddenException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.mapper.ServiceShiftMapper;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.repository.ServiceShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ServiceShiftService {
    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);

    @Autowired
    ServiceShiftRepository shiftRepository;

    @Autowired
    ServiceShiftMapper shiftMapper;

    public ServiceShiftEntity createShift(ShiftType shift, LocalDate date){
        ServiceShiftEntity shiftEntity = new ServiceShiftEntity ();
        shiftEntity.setShift (shift);
        shiftEntity.setDate (date);
        shiftEntity.setActive (true);
        shiftEntity.setOpening_time (Timestamp.valueOf (LocalDateTime.now ()));
        shiftEntity.setValid (true);
        try {
            shiftRepository.save (shiftEntity);
            log.info ("TURNO CRIADO: {}, {}", shift, date.toString ());
        } catch (RuntimeException e) {
            log.error ("NÃO FOI POSSÍVEL CRIAR TURNO. "+ shift +" "+date.toString()+" "+ e);
            throw new ServerException ("NÃO FOI POSSÍVEL CRIAR TURNO. "+ shift + ", "+ date.toString ());
        }
        return shiftEntity;
    }

    public ServiceShiftEntity closeShift(int id, UserDTO activeUser){
        ServiceShiftEntity shiftEntity = shiftRepository.findById (id).orElseThrow (
                ()-> { log.warn ("Turno não encontrado. ID: {}", id);
                       return new NotFoundException ("Turno de ID: "+id+" não encontrado");
                }
        );
        shiftEntity.setClosed_by (activeUser.lpna_identifier ());
        shiftEntity.setClosing_time (Timestamp.valueOf (LocalDateTime.now ()));
        shiftEntity.setActive (false);
        try{
            shiftRepository.save (shiftEntity);
            log.info ("TURNO {} FECHADO POR {}",shiftEntity, activeUser.lpna_identifier ());
        } catch (RuntimeException error) {
            log.error ("ERRO AO FECHAR TURNO "+ shiftEntity+" "+error);
            throw new ServerException ("Erro do servidor ao encerrar turno.");
        }
        return shiftEntity;
    }

    public ServiceShiftEntity invalidateShift(int id, UserDTO activeUser){
        ServiceShiftEntity shiftEntity = shiftRepository.findById (id).orElseThrow (
                ()-> { log.warn ("Turno não encontrado. ID: {}", id);
                    return new NotFoundException ("Turno de ID: "+id+" não encontrado");
                }
        );
        shiftEntity.setClosed_by (activeUser.lpna_identifier ());
        shiftEntity.setClosing_time (Timestamp.valueOf (LocalDateTime.now ()));
        shiftEntity.setActive (false);
        shiftEntity.setValid (false);
        try{
            shiftRepository.save (shiftEntity);
            log.info ("TURNO {} INVALIDADO POR {}",shiftEntity, activeUser.lpna_identifier ());

        } catch (RuntimeException e) {
            log.error ("ERRO AO INVALIDAR TURNO "+ shiftEntity+" "+e);
            throw new ServerException ("Erro do servidor ao invalidar turno.");
        }
        return shiftEntity;
    }

    public ServiceShiftEntity updateShiftAfterClose(int id, UserDTO activeUser, UpdateShiftDTO updateShiftDTO){
        ServiceShiftEntity shiftEntity = shiftRepository.findById (id).orElseThrow (
                ()-> { log.warn ("Turno não encontrado. ID: {}", id);
                    return new NotFoundException ("Turno de ID: "+id+" não encontrado");
                }
        );
        String closedByLPNA = shiftEntity.getClosed_by ();
        if(!Objects.equals (closedByLPNA, activeUser.lpna_identifier ())){
            log.warn ("USUÁRIO {} NÃO É O USUÁRIO QUE FECHOU O LIVRO {}({})", activeUser.lpna_identifier (), shiftEntity, closedByLPNA);
            throw new ForbiddenException ("Usuário que fechou o livro nao é o mesmo da alteração.");
        }

        shiftMapper.updateShiftFromDTO (updateShiftDTO, shiftEntity);

        try{
            shiftRepository.save (shiftEntity);
            log.info ("TURNO {} ATUALIZADO POR {}",shiftEntity, activeUser.lpna_identifier ());

        } catch (RuntimeException e) {
            log.error ("ERRO AO ATUALIZAR TURNO "+ shiftEntity+" "+e);
            throw new ServerException ("Erro do servidor ao atualizar turno.");
        }
        return shiftEntity;
    }


}
