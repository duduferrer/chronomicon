package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.*;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.entities.CoreUserInformationEntity;
import bh.app.chronomicon.repository.AtcoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtcoService {

    private static final Logger log = LoggerFactory.getLogger(AtcoService.class);

    @Autowired
    private AuthService authService;
    @Autowired
    private AtcoRepository userRepository;


    public List<AtcoDTO> findSupervisors() {
        log.info ("EXIBINDO LISTA DE SUPERVISORES");
        return userRepository.findSupsOrderByHierarchy()
                .stream()
                .map(user -> new AtcoDTO(user)).toList();
    }

    public List<AtcoDTO> findUsers() {
        log.info ("EXIBINDO LISTA DE USUÁRIOS");
        return userRepository.findActiveUsersOrderByHierarchy()
                .stream()
                .map(user -> new AtcoDTO(user))
                .toList();
    }

    public List<AtcoDTO> findInstructors() {
        log.info ("EXIBINDO LISTA DE INSTRUTORES");
        return userRepository.findInstsOrderByHierarchy()
                .stream()
                .map(user -> new AtcoDTO(user))
                .toList();
    }

    public List<AtcoDTO> findTrainees() {
        log.info ("EXIBINDO LISTA DE ESTAGIÁRIOS");
        return userRepository.findTraineesOrderByHierarchy()
                .stream()
                .map(user -> new AtcoDTO(user))
                .toList();
    }

    public List<AtcoDTO> findOnlyOperators() {
        log.info ("EXIBINDO LISTA DE OPERADORES");
        return userRepository.findOnlyOpsOrderByHierarchy()
                .stream()
                .map(user -> new AtcoDTO(user))
                .toList();
    }

    public AtcoDTO findUserById(Long id) {
        try{
            AtcoEntity user = userRepository.findUserById(id);
            log.info ("EXIBINDO OPERADOR DE ID: {}",id);
            return new AtcoDTO(user);
        } catch (RuntimeException e) {
            log.warn ("ERRO AO EXIBIR OPERADOR DE ID: {}",id);
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    @Transactional
    public AtcoEntity createNewUser(CreateUserDTO createUserDTO) {
        CreateAtcoDTO createAtcoDTO = createUserDTO.createAtcoDTO();
        checkLpnaAlreadyRegistered(createAtcoDTO.lpna_identifier());
        List<AtcoEntity> usersFromRank = userRepository.getUsersOrderedByLowestHierarchyFromRank(createUserDTO.coreUserInformationDTO().rank ());
        short userHierarchy;
        if(usersFromRank.isEmpty ()){
            userHierarchy = (short) 0;
        }else{
            userHierarchy = (short)(usersFromRank.get (0).getHierarchy () + (short)1);
        }
        hierarchyDeconfliction(userHierarchy);
        CoreUserInformationEntity coreUserInformation = authService.createCoreUser(createUserDTO);
        AtcoEntity atcoEntity = new AtcoEntity();
        atcoEntity.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        atcoEntity.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        atcoEntity.setLpna_identifier(createAtcoDTO.lpna_identifier());
        atcoEntity.setHierarchy(userHierarchy);
        atcoEntity.setInstructor(createAtcoDTO.instructor());
        atcoEntity.setSupervisor(createAtcoDTO.supervisor());
        atcoEntity.setSupervisor(createAtcoDTO.trainee());
        atcoEntity.setCoreUserInformationEntity(coreUserInformation);
        
        try{
            userRepository.save(atcoEntity);
        }catch(RuntimeException e){
            log.error ("ERRO AO CRIAR USUÁRIO: {}",atcoEntity.getLpna_identifier(), e);
            throw new ServerException ("Erro ao Salvar usuário.");
        }
        log.info ("USUÁRIO CRIADO COM SUCESSO ({}) POR: {}", atcoEntity.getLpna_identifier (), authService.getActiveUserUsername());
        return atcoEntity;
    }

    public AtcoDTO findUserByLPNAReturnDTO(String lpna){
        AtcoEntity user = findUser(lpna);
        log.info ("EXIBIDO USUÁRIO: {}", lpna);
        return new AtcoDTO(user);
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
        log.info ("HIERARQUIA ATUALIZADA DEVIDO INSERÇÃO DE HIERARQUIA {}", hierarchy);
    }

    private void shiftInactiveUsersHierarchy(short hierarchy){
        userRepository.shiftInactiveUsersHierarchy(hierarchy);
        userRepository.flush();
        log.info ("HIERARQUIA ATUALIZADA DEVIDO INSERÇÃO DE HIERARQUIA {}", hierarchy);
    }

    private void hierarchyDeconfliction(short hierarchy){
        if(hierarchyAlreadyExists(hierarchy) && hierarchy!=1001){
            shiftActiveUsersHierarchy(hierarchy);
        }else{
            shiftInactiveUsersHierarchy(hierarchy);
        }

    }

    public AtcoEntity findUser(String lpna){
        AtcoEntity user = userRepository.findUserByLPNA(lpna);
        if(user==null){
            log.info ("USUÁRIO {} PROCURADO E NAO ENCONTRADO", lpna);
            throw new NotFoundException("Usuário com LPNA:"+ lpna +"não encontrado.");
        }else{
            return user;
        }
    }

    @Transactional
    public void activateUser(String lpna){
        AtcoEntity user = findUser(lpna);
        if(user.isActive()){
            log.warn ("TENTATIVA DE ATIVAR USUÁRIO {}", lpna);
            throw new ConflictException("Usuário já está ativo. Apenas usuários inativos podem ser ativados.");
        }
        List<AtcoEntity> usersFromRank = userRepository.getUsersOrderedByLowestHierarchyFromRank(user.getCoreUserInformationEntity().getRank());
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
            log.info ("USUÁRIO {} ATIVADO COM SUCESSO, POR {}", lpna, authService.getActiveUserUsername());
        }catch(RuntimeException e){
            log.error ("TENTATIVA DE ATIVAÇAO USUÁRIO {} POR: {}. {}", lpna, authService.getActiveUserUsername(),e.getMessage());
            throw new ServerException ("HOUVE UM ERRO AO ATIVAR USUÁRIO");
        }

    }

    @Transactional
    public void deactivateUser(String lpna){
        AtcoEntity user = findUser(lpna);
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
            log.info ("USUÁRIO {} DESATIVADO COM SUCESSO POR: {}", lpna, authService.getActiveUserUsername());
        }catch(RuntimeException e){
            log.error ("TENTATIVA DE DESATIVAR USUÁRIO {} POR: {}, {}", lpna, authService.getActiveUserUsername(),e.getMessage());
            throw new ServerException ("HOUVE UM ERRO AO DESATIVAR USUÁRIO");
        }
    }

    @Transactional
    public void updateUser(String lpna, UpdateUserDTO updateDto){
        AtcoEntity user = findUser (lpna);
        if(updateDto.supervisor ()!=null){
            user.setSupervisor (updateDto.supervisor ());
        }
        if(updateDto.service_name ()!=null){
            user.getCoreUserInformationEntity().setService_name(updateDto.service_name ());
        }
        if(updateDto.full_name ()!=null){
            user.getCoreUserInformationEntity().setFullName (updateDto.full_name ());
        }
        if(updateDto.trainee ()!=null){
            user.setTrainee (updateDto.trainee ());
        }
        if(updateDto.instructor ()!=null){
            user.setInstructor (updateDto.instructor ());
        }
        if(updateDto.rank ()!=null){
            user.getCoreUserInformationEntity().setRank(updateDto.rank ());
        }
        if(updateDto.lpna_identifier ()!=null){
            user.setLpna_identifier (updateDto.lpna_identifier ());
        }
        try{
            userRepository.save (user);
            log.info("Usuário {} atualizado por {}", user.getId(), authService.getActiveUserUsername());

        } catch (RuntimeException e) {
            log.error ("Erro ao atualizar usuário: {}. Com os dados: {}. {}", lpna, updateDto, e.getMessage());
            throw new ServerException ("Erro ao atualizar usuário.");
        }


    }



}
