package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.NotFoundException;
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
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    @Transactional
    public UserDTO createNewUser(UserDTO user) {

        checkLpnaAlreadyRegistered(user.lpna_identifier());
        hierarchyDeconfliction(user.hierarchy());

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
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    private void checkLpnaAlreadyRegistered(String lpna) throws ConflictException {
        if(userRepository.existsByLpnaIdentifier(lpna)){
            throw new ConflictException("Ja existe um usuario cadastrado usando esse indicativo LPNA: "+ lpna);
        }
    }

    private boolean hierarchyAlreadyExists(short hierarchy){
        return userRepository.existsByHierarchy(hierarchy);
    }


    private void shiftActiveUsersHierarchy(short hierarchy){
        userRepository.shiftActiveUsersHierarchy(hierarchy);
        userRepository.flush();
    }

    private void shiftInactiveUsersHierarchy(short hierarchy){
        userRepository.shiftInactiveUsersHierarchy(hierarchy);
        userRepository.flush();
    }

    private void hierarchyDeconfliction(short hierarchy){
        if(hierarchyAlreadyExists(hierarchy) && hierarchy!=1001){
            shiftActiveUsersHierarchy(hierarchy);
        }else{
            shiftInactiveUsersHierarchy(hierarchy);
        }

    }

    @Transactional
    public void activateUser(String lpna){
        UserEntity user = userRepository.findUserByLPNA(lpna);
        if(user.isActive()){
            throw new ConflictException("Usuário já está ativo. Apenas usuários inativos podem ser ativados.");
        }
        short reactivatedUserHierarchy =(short)(userRepository.getUsersOrderedByLowestHierarchyFromRank(user.getRank()).get(0).getHierarchy() + 1);
        hierarchyDeconfliction(reactivatedUserHierarchy);
        userRepository.flush();
        userRepository.updateUserHierarchy(reactivatedUserHierarchy, lpna);
        userRepository.flush();
        userRepository.activateUser(lpna);

    }

    @Transactional
    public void deactivateUser(String lpna){
        UserEntity user = userRepository.findUserByLPNA(lpna);
        if(!user.isActive()){
            throw new ConflictException("Usuário já está inativo. Apenas usuários ativos podem ser desativados.");
        }
        hierarchyDeconfliction((short) 1001);
        userRepository.flush();
        userRepository.updateUserHierarchy((short) 1001, lpna);
        userRepository.flush();
        userRepository.deactivateUser(lpna);

    }



}
