package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CreateUserDTO;
import bh.app.chronomicon.dto.UserDTO;
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
        return new UserDTO(user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
    }

    public UserDTO createNewUser(CreateUserDTO user) {
        if(userRepository.existsByLpnaIdentifier(user.lpna_identifier())){
            throw new IllegalArgumentException("LPNA já cadastrado.");
        }
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



}
