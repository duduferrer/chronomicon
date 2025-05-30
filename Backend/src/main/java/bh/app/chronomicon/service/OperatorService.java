package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CreateOperatorDTO;
import bh.app.chronomicon.exception.ServerException;
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

    public CreateOperatorDTO createOperator(String lpna, ShiftType op_shift, boolean isSupervisor,
                                            boolean isTrainee, boolean isInstructor, ServiceShiftEntity serviceShift){
       bh.app.chronomicon.dto.UserDTO userDTO = userService.findUserByLPNA (lpna);
       UserEntity user = new UserEntity (userDTO);
//       TODO: SE NAO TIVER SHIFT, CRIA UM, SE NAO TIVER ROSTER, CRIA UM.

       try {
           OperatorEntity operator = new OperatorEntity (user, Duration.ZERO, op_shift, isSupervisor,
                   isTrainee, isInstructor, serviceShift);
           operatorRepository.save(operator);
           log.info ("Operador {} criado com sucesso no turno {}", lpna, serviceShift.getId ());
           return new CreateOperatorDTO(userDTO, op_shift, operator.getId (), isSupervisor, isTrainee, isInstructor);
       }catch (RuntimeException e){
           log.error ("Erro ao cadastrar {} no turno {}", lpna, serviceShift.getId ());
           throw new ServerException ("Erro ao cadastrar " + lpna + " no turno " + serviceShift.getId ());
       }

    }
}
