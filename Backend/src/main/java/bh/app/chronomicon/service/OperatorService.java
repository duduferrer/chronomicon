package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.*;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.mapper.OperatorMapper;
import bh.app.chronomicon.model.entities.OperatorEntity;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.repository.OperatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OperatorService {
    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);

    @Autowired
    OperatorRepository operatorRepository;

    @Autowired
    UserService userService;

    @Autowired
    OperatorMapper operatorMapper;

    public CreateOperatorDTO createOperator(String lpna, ShiftType op_shift, boolean isSupervisor,
                                            boolean isTrainee, boolean isInstructor, ServiceShiftEntity serviceShift){
       UserDTO userDTO = userService.findUserByLPNA (lpna);
       UserEntity user = new UserEntity (userDTO);

       try {
           OperatorEntity operator = new OperatorEntity (user, Duration.ZERO, op_shift, isSupervisor,
                   isTrainee, isInstructor, serviceShift);
           operatorRepository.save(operator);
           log.info ("Operador {} criado com sucesso no turno {}", lpna, serviceShift.getId ());
           return new CreateOperatorDTO(userDTO, op_shift, operator.getId (), isSupervisor, isTrainee, isInstructor);
       }catch (RuntimeException e){
           log.error ("Erro ao cadastrar "+ lpna +"no turno "+ serviceShift.getId () +" Erro: " + e );
           throw new ServerException ("Erro ao cadastrar " + lpna + " no turno " + serviceShift.getId ());
       }

    }

    public OperatorDTO updateOperator(String operatorID, UpdateOperatorDTO updateOperatorDTO){
        OperatorEntity operatorEntity = operatorRepository.findById (operatorID).orElseThrow ( ()->{
                log.warn ("OPERADOR NÃO ENCONTRADO. ID: {}", operatorID);
                return new NotFoundException ("Operador ID: "+ operatorID+" não encontrado.");
        });
        operatorMapper.updateOperatorFromDTO (updateOperatorDTO, operatorEntity);
        try {
            operatorRepository.save (operatorEntity);
            log.info ("Operador {} atualizado com sucesso", operatorEntity.getId ());
        }catch (RuntimeException e){
            log.error ("Erro ao atualizar "+ operatorEntity.getId () + " Erro: " + e );
            throw new ServerException ("Erro ao atualizar "+ operatorEntity.getId () + " Erro: " + e );
        }
        return new OperatorDTO (operatorEntity.getId (),
                operatorEntity.getUser ().getLpna_identifier (), operatorEntity.getWorkload (),
                operatorEntity.getShift_type ());
    }
}
