package bh.app.chronomicon.service;


import bh.app.chronomicon.dto.RecoverPasswordDTO;
import bh.app.chronomicon.exception.NotActiveEventException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.ForgottenPasswordEntity;
import bh.app.chronomicon.model.entities.SystemUserEntity;
import bh.app.chronomicon.repository.ForgottenPasswordRepository;
import bh.app.chronomicon.repository.SystemUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordRecoveryService {
    private static final Logger log = LoggerFactory.getLogger(PasswordRecoveryService.class);

    @Autowired
    ForgottenPasswordRepository forgottenPasswordRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SystemUserRepository systemUserRepository;
    @Autowired
    AuthService authService;

    public String createPasswordRecoveryToken(String email){
        SystemUserEntity systemUserEntity = authService.getUserByEmail (email);
        String token = UUID.randomUUID ().toString ();
        LocalDateTime expiresAt = LocalDateTime.now ().plusMinutes (15);
        ForgottenPasswordEntity forgottenPasswordEntity = new ForgottenPasswordEntity (systemUserEntity, token, expiresAt, false);
        try{
            ForgottenPasswordEntity oldPasswordRecovery = forgottenPasswordRepository.findBySystemUserEntity (systemUserEntity);
            if(oldPasswordRecovery!=null){
                forgottenPasswordRepository.delete (oldPasswordRecovery);
                log.info ("Deletado token de mudança de senha passado. {}", email);
            }
            forgottenPasswordRepository.save (forgottenPasswordEntity);
            return forgottenPasswordEntity.getPasswordRefreshToken ();
        } catch (RuntimeException e) {
            log.error ("Erro ao criar token para recuperar senha. ({}): {}", systemUserEntity.getCoreUserInformation().getSaram (), e.getMessage ());
            throw new ServerException ("Erro ao criar token para recuperar senha.");
        }
    }

    public void resetPassword(String token, RecoverPasswordDTO updatePasswordDTO){
        ForgottenPasswordEntity forgottenPasswordEntity = forgottenPasswordRepository.findByPasswordRefreshToken (token)
                .orElseThrow (()->new NotFoundException("Token inválido"));
        if(forgottenPasswordEntity.isUsed ()){
            log.warn ("Token já usado para reset de senha, ({}, {})", forgottenPasswordEntity.getSystemUserEntity ().getCoreUserInformation().getSaram (), token);
            throw new NotActiveEventException ("Token já usado.");
        }
        if(forgottenPasswordEntity.getExpiresAt ().isBefore (LocalDateTime.now ())){
            log.warn ("Token reset de senha expirado, ({}, {}, {})", forgottenPasswordEntity.getSystemUserEntity ().getCoreUserInformation().getSaram (), token, forgottenPasswordEntity.getExpiresAt ().toString ());
            throw new NotActiveEventException ("Token expirado.");
        }

        SystemUserEntity systemUserEntity = forgottenPasswordEntity.getSystemUserEntity ();
        systemUserEntity.setPassword (passwordEncoder.encode (updatePasswordDTO.newPassword ()));
        systemUserRepository.save (systemUserEntity);
        forgottenPasswordEntity.setUsed (true);
        forgottenPasswordRepository.save (forgottenPasswordEntity);
    }


}
