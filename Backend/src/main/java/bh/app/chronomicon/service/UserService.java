package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CreateUserDTO;
import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.LpnaAlreadyExistsException;
import bh.app.chronomicon.exception.UserNotFoundException;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findSupervisors() {
        return userRepository.findSupsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                        .toList();
    }

    public List<UserDTO> findUsers() {
        return userRepository.findActiveUsersOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findInstructors() {
        return userRepository.findInstsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findTrainees() {
        return userRepository.findTraineesOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findOnlyOperators() {
        return userRepository.findOnlyOpsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public UserDTO findUserById(Long id) {
        UserEntity user = userRepository.findUserById(id);
        try{
            return new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                            user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    public UserDTO createNewUser(CreateUserDTO user) {

        checkLpnaAlreadyRegistered(user.lpna_identifier());

        UserEntity userEntity = new UserEntity();
        userEntity.setLpna_identifier(user.lpna_identifier());
        userEntity.setRank(user.rank());
        userEntity.setFull_name(user.full_name());
        userEntity.setHierarchy(user.hierarchy());
        userEntity.setService_name(user.service_name());
        userEntity.setSupervisor(user.supervisor());
        userEntity.setInstructor(user.instructor());
        userEntity.setTrainee(user.trainee());
        userRepository.save(userEntity);
        return new UserDTO(userEntity);
    }

    public UserDTO findUserByLPNA(String lpna){
        UserEntity user = userRepository.findUserByLPNA(lpna);
        try{
            return new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                    user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
        }catch (RuntimeException e){
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    private void checkLpnaAlreadyRegistered(String lpna) throws LpnaAlreadyExistsException{
        if(userRepository.existsByLpnaIdentifier(lpna)){
            throw new LpnaAlreadyExistsException("Ja existe um usuario cadastrado usando esse indicativo LPNA: "+ lpna);
        }
    }



}
