package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.*;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.mapper.OperatorMapper;
import bh.app.chronomicon.model.entities.OperatorEntity;
import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.model.enums.WorkloadOperation;
import bh.app.chronomicon.repository.OperatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class OperatorService {
    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);

    @Autowired
    OperatorRepository operatorRepository;

    @Autowired
    UserService userService;

    @Autowired
    OperatorMapper operatorMapper;

    @Autowired
    ServiceShiftService serviceShiftService;

    public CreateOperatorDTO createOperator(String lpna, ShiftType op_shift, boolean isSupervisor,
                                            boolean isTrainee, boolean isInstructor, int serviceShiftID){
       UserDTO userDTO = userService.findUserByLPNAReturnDTO (lpna);
       UserEntity user = new UserEntity (userDTO);
       ServiceShiftEntity serviceShift = serviceShiftService.getServiceShiftByID (serviceShiftID);

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
            log.info ("Operador {} atualizado com sucesso", operatorID);
        }catch (RuntimeException e){
            log.error ("Erro ao atualizar "+ operatorID + " Erro: " + e );
            throw new ServerException ("Erro ao atualizar "+ operatorID);
        }
        return new OperatorDTO (operatorEntity.getId (),
                operatorEntity.getUser ().getLpna_identifier (), operatorEntity.getWorkload (),
                operatorEntity.getShift_type (), operatorEntity.isSupervisor (), operatorEntity.isInstructor (),
                operatorEntity.isTrainee ());
    }

    public void deleteOperator(String operatorID){
        OperatorEntity operatorEntity = operatorRepository.findById (operatorID).orElseThrow ( ()->{
            log.warn ("OPERADOR NÃO ENCONTRADO. ID: {}", operatorID);
            return new NotFoundException ("Operador ID: "+ operatorID+" não encontrado.");
        });
        try {
            operatorRepository.delete (operatorEntity);
            log.info ("Operador {} deletado com sucesso", operatorID);
        }catch (RuntimeException e){
            log.error ("Erro ao deletar "+ operatorID + " Erro: " + e );
            throw new ServerException ("Erro ao deletar "+ operatorID);
        }
    }

    public List<OperatorDTO> getAllOperators(int shiftID){
        log.info ("EXIBINDO LISTA DE TODOS OS INTEGRANTES DO TURNO");
        return operatorRepository.findAllStaffOrderByHierarchy (shiftID)
                .stream()
                .map(operator -> new OperatorDTO (operator.getId (), operator.getUser ().getLpna_identifier (),
                        operator.getWorkload (), operator.getShift_type (), operator.isSupervisor (), operator.isInstructor(),
                        operator.isTrainee()))
                .toList();
    }

    public List<OperatorDTO> getOnlyOperators(int shiftID){
        log.info ("EXIBINDO LISTA DE OPERADORES DO TURNO");
        return operatorRepository.findOperatorsOnlyOrderByHierarchy (shiftID)
                .stream()
                .map(operator -> new OperatorDTO (operator.getId (), operator.getUser ().getLpna_identifier (),
                        operator.getWorkload (), operator.getShift_type (), operator.isSupervisor (), operator.isInstructor(),
                        operator.isTrainee()))
                .toList();
    }

    public List<OperatorDTO> getSupervisors(int shiftID){
        log.info ("EXIBINDO LISTA DE SUPERVISORES DO TURNO");
        return operatorRepository.findSupsOrderByHierarchy (shiftID)
                .stream()
                .map(operator -> new OperatorDTO (operator.getId (), operator.getUser ().getLpna_identifier (),
                        operator.getWorkload (), operator.getShift_type (), operator.isSupervisor (), operator.isInstructor(),
                        operator.isTrainee()))
                .toList();
    }

    public List<OperatorDTO> getInstructors(int shiftID){
        log.info ("EXIBINDO LISTA DE INSTRUTORES DO TURNO");
        return operatorRepository.findInstsOrderByHierarchy (shiftID)
                .stream()
                .map(operator -> new OperatorDTO (operator.getId (), operator.getUser ().getLpna_identifier (),
                        operator.getWorkload (), operator.getShift_type (), operator.isSupervisor (), operator.isInstructor(),
                        operator.isTrainee()))
                .toList();
    }

    public List<OperatorDTO> getTrainees(int shiftID){
        log.info ("EXIBINDO LISTA DE ESTAGIARIOS DO TURNO");
        return operatorRepository.findTraineesOrderByHierarchy (shiftID)
                .stream()
                .map(operator -> new OperatorDTO (operator.getId (), operator.getUser ().getLpna_identifier (),
                        operator.getWorkload (), operator.getShift_type (), operator.isSupervisor (), operator.isInstructor(),
                        operator.isTrainee()))
                .toList();
    }

    public OperatorDTO updateWorkload(String id, int hours, int minutes, WorkloadOperation operation){
        OperatorEntity operatorEntity = operatorRepository.findById (id).orElseThrow ( ()->{
            log.warn ("OPERADOR NÃO ENCONTRADO. ID: {}", id);
            return new NotFoundException ("Operador ID: "+ id+" não encontrado.");
        });
        try{
            Duration delta = Duration.ofHours (hours).plusMinutes (minutes);
            operatorEntity.setWorkload (delta, operation);
            operatorRepository.save (operatorEntity);
        } catch (RuntimeException e) {
            log.error ("Erro ao modificar Carga Horária. Id: "+ id + ". Horas: "+hours+", Minutos: "+minutes+" Operação: " + operation + ". Erro: " + e );
            throw new ServerException ("Erro ao modificar carga horária.");
        }
        return new OperatorDTO (id, operatorEntity.getUser ().getLpna_identifier (), operatorEntity.getWorkload (),
                operatorEntity.getShift_type (), operatorEntity.isSupervisor (), operatorEntity.isInstructor (), operatorEntity.isTrainee ());
    }
}

