package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.LpnaAlreadyExistsException;
import bh.app.chronomicon.exception.UserNotFoundException;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.repository.UserRepository;
import jakarta.transaction.Transactional;
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
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                        .toList();
    }

    public List<UserDTO> findUsers() {
        return userRepository.findActiveUsersOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findInstructors() {
        return userRepository.findInstsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findTrainees() {
        return userRepository.findTraineesOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findOnlyOperators() {
        return userRepository.findOnlyOpsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public UserDTO findUserById(Long id) {
        UserEntity user = userRepository.findUserById(id);
        try{
            return new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                    user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    @Transactional
    public UserDTO createNewUser(UserDTO user) {

        checkLpnaAlreadyRegistered(user.lpna_identifier());


        if(hierarchyAlreadyExists(user.hierarchy())){
            shiftActiveUsersHierarchy(user.hierarchy());
        }

        UserEntity userEntity = new UserEntity(user);
        userRepository.save(userEntity);
        return new UserDTO(userEntity);
    }

    public UserDTO findUserByLPNA(String lpna){
        UserEntity user = userRepository.findUserByLPNA(lpna);
        try{
            return new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                    user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
        }catch (RuntimeException e){
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    private void checkLpnaAlreadyRegistered(String lpna) throws LpnaAlreadyExistsException{
        if(userRepository.existsByLpnaIdentifier(lpna)){
            throw new LpnaAlreadyExistsException("Ja existe um usuario cadastrado usando esse indicativo LPNA: "+ lpna);
        }
    }

    private boolean hierarchyAlreadyExists(short hierarchy){
        return userRepository.existsByHierarchy(hierarchy);
    }


    private void shiftActiveUsersHierarchy(short hierarchy){
        userRepository.shiftActiveUsersHierarchy(hierarchy);
        userRepository.flush();
    }



}
