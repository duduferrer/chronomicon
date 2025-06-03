package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CreateUserDTO;
import bh.app.chronomicon.dto.UpdateUserDTO;
import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findSupervisors() {
        log.info ("EXIBINDO LISTA DE SUPERVISORES");
        return userRepository.findSupsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                        .toList();
    }

    public List<UserDTO> findUsers() {
        log.info ("EXIBINDO LISTA DE USUÁRIOS");
        return userRepository.findActiveUsersOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findInstructors() {
        log.info ("EXIBINDO LISTA DE INSTRUTORES");
        return userRepository.findInstsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findTrainees() {
        log.info ("EXIBINDO LISTA DE ESTAGIÁRIOS");
        return userRepository.findTraineesOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public List<UserDTO> findOnlyOperators() {
        log.info ("EXIBINDO LISTA DE OPERADORES");
        return userRepository.findOnlyOpsOrderByHierarchy()
                .stream()
                .map(user -> new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                        user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee()))
                .toList();
    }

    public UserDTO findUserById(Long id) {
        try{
            UserEntity user = userRepository.findUserById(id);
            log.info ("EXIBINDO OPERADOR DE ID: {}",id);
            return new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                    user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
        } catch (RuntimeException e) {
            log.warn ("ERRO AO EXIBIR OPERADOR DE ID: {}",id);
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    @Transactional
    public UserDTO createNewUser(CreateUserDTO user) {

        checkLpnaAlreadyRegistered(user.lpna_identifier());
        List<UserEntity> usersFromRank = userRepository.getUsersOrderedByLowestHierarchyFromRank(user.rank ());
        short userHierarchy;
        if(usersFromRank.isEmpty ()){
            userHierarchy = (short) 0;
        }else{
            userHierarchy = (short)(usersFromRank.get (0).getHierarchy () + (short)1);
        }
        hierarchyDeconfliction(userHierarchy);

        UserEntity userEntity = new UserEntity (user, userHierarchy);
        try{
            userRepository.save(userEntity);
        }catch(RuntimeException e){
            log.error ("ERRO AO CRIAR USUÁRIO: {}",user, e);
            throw new ServerException ("Erro ao Salvar usuário.");
        }
        log.info ("USUÁRIO CRIADO COM SUCESSO: {}", user.lpna_identifier ());
        return new UserDTO (userEntity);
    }

    public UserDTO findUserByLPNA(String lpna){
        UserEntity user = findUser(lpna);
        log.info ("EXIBIDO USUÁRIO: {}", lpna);
        return new UserDTO (user.getLpna_identifier(), user.getFull_name(), user.getService_name(),
                    user.getRank(), user.getHierarchy(), user.isSupervisor(), user.isInstructor(), user.isTrainee());
    }

    private void checkLpnaAlreadyRegistered(String lpna) throws ConflictException {
        if(userRepository.existsByLpnaIdentifier(lpna)){
            log.warn ("LPNA {} CONSTA COMO JÁ CADASTRADA", lpna);
            throw new ConflictException("Ja existe um usuario cadastrado usando esse indicativo LPNA: "+ lpna);
        }
    }

    private boolean hierarchyAlreadyExists(short hierarchy){
        return userRepository.existsByHierarchy(hierarchy);
    }


    private void shiftActiveUsersHierarchy(short hierarchy){
        userRepository.shiftActiveUsersHierarchy(hierarchy);
        userRepository.flush();
        log.info ("HIERARQUIA ATUALIZADA DEVIDO: {}", hierarchy);
    }

    private void shiftInactiveUsersHierarchy(short hierarchy){
        userRepository.shiftInactiveUsersHierarchy(hierarchy);
        userRepository.flush();
        log.info ("HIERARQUIA ATUALIZADA DEVIDO: {}", hierarchy);
    }

    private void hierarchyDeconfliction(short hierarchy){
        if(hierarchyAlreadyExists(hierarchy) && hierarchy!=1001){
            shiftActiveUsersHierarchy(hierarchy);
        }else{
            shiftInactiveUsersHierarchy(hierarchy);
        }

    }

    private UserEntity findUser(String lpna){
        UserEntity user = userRepository.findUserByLPNA(lpna);
        if(user==null){
            log.info ("USUÁRIO {} PROCURADO E NAO ENCONTRADO", lpna);
            throw new NotFoundException("Usuário com LPNA:"+ lpna +"não encontrado.");
        }else{
            return user;
        }
    }

    @Transactional
    public void activateUser(String lpna){
        UserEntity user = findUser(lpna);
        if(user.isActive()){
            log.warn ("TENTATIVA DE ATIVAR USUÁRIO {}", lpna);
            throw new ConflictException("Usuário já está ativo. Apenas usuários inativos podem ser ativados.");
        }
        List<UserEntity> usersFromRank = userRepository.getUsersOrderedByLowestHierarchyFromRank(user.getRank());
        short reactivatedUserHierarchy;
        if(usersFromRank.isEmpty ()){
            reactivatedUserHierarchy = (short) 0;
        }else{
            reactivatedUserHierarchy = (short)(usersFromRank.get (0).getHierarchy () + (short)1);
        }
        hierarchyDeconfliction(reactivatedUserHierarchy);
        userRepository.flush();
        userRepository.updateUserHierarchy(reactivatedUserHierarchy, lpna);
        userRepository.flush();
        try{
            userRepository.activateUser(lpna);
            log.info ("USUÁRIO {} ATIVADO COM SUCESSO", lpna);
        }catch(RuntimeException e){
            log.error ("TENTATIVA DE ATIVAÇAO USUÁRIO {}", lpna, e);
            throw new ServerException ("HOUVE UM ERRO AO ATIVAR USUÁRIO");
        }

    }

    @Transactional
    public void deactivateUser(String lpna){
        UserEntity user = findUser(lpna);
        if(!user.isActive()){
            log.warn ("TENTATIVA DE DESATIVAR USUÁRIO {}", lpna);
            throw new ConflictException("Usuário já está inativo. Apenas usuários ativos podem ser desativados.");
        }
        hierarchyDeconfliction((short) 1001);
        userRepository.flush();
        userRepository.updateUserHierarchy((short) 1001, lpna);
        userRepository.flush();
        try{
            userRepository.deactivateUser(lpna);
            log.info ("USUÁRIO {} DESATIVADO COM SUCESSO", lpna);
        }catch(RuntimeException e){
            log.error ("TENTATIVA DE DESATIVAR USUÁRIO {}", lpna, e);
            throw new ServerException ("HOUVE UM ERRO AO DESATIVAR USUÁRIO");
        }
    }

    @Transactional
    public void updateUser(String lpna, UpdateUserDTO updateDto){
        UserEntity user = findUser (lpna);
        if(updateDto.supervisor ()!=null){
            user.setSupervisor (updateDto.supervisor ());
        }
        if(updateDto.service_name ()!=null){
            user.setService_name (updateDto.service_name ());
        }
        if(updateDto.full_name ()!=null){
            user.setFull_name (updateDto.full_name ());
        }
        if(updateDto.trainee ()!=null){
            user.setTrainee (updateDto.trainee ());
        }
        if(updateDto.instructor ()!=null){
            user.setInstructor (updateDto.instructor ());
        }
        if(updateDto.rank ()!=null){
            user.setRank (updateDto.rank ());
        }
        if(updateDto.lpna_identifier ()!=null){
            user.setLpna_identifier (updateDto.lpna_identifier ());
        }
        try{
            userRepository.save (user);

        } catch (RuntimeException e) {
            log.error ("Erro ao atualizar usuário: {}. Com os dados: {}", lpna, updateDto, e);
            throw new ServerException ("Erro ao atualizar usuário.");
        }


    }



}
