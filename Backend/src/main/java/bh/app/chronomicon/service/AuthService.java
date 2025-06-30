package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.AuthenticationDTO;
import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.ForbiddenException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.SystemUserEntity;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.repository.SystemUserRepository;
import bh.app.chronomicon.security.JwtUtil;
import bh.app.chronomicon.security.ValidPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    SystemUserRepository systemUserRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return systemUserRepository.findUserBySaram (username);
    }

    public UserDTO getUser(String token){
        String saram = jwtUtil.getUsername(token);
        UserEntity user = systemUserRepository.findUserBySaram (saram).getUser ();
        return new UserDTO (user);
    }

    public SystemUserEntity getSystemUser(String token){
        String saram = jwtUtil.getUsername (token);
        return systemUserRepository.findUserBySaram (saram);
    }

    public void updateLastLogin(String token){
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        SystemUserEntity systemUserEntity = getSystemUser (token);
        systemUserEntity.setLast_login (now);
        try{
            systemUserRepository.save (systemUserEntity);
        }catch(RuntimeException e){
            log.error ("Erro ao atualizar hora do ultimo login {}, Hora do ultimo login: {}, Erro: {}",
                    systemUserEntity.getSaram (),
                    now,
                    e.getMessage ());
            throw new ServerException ("Erro ao atualizar hora do ultimo login");
        }
    }

    public void updatePassword(String token, AuthenticationDTO authenticationDTO, @ValidPassword String newPassword){

        if(!jwtUtil.isTokenValid (token)){
            log.warn ("Tentativa de troca de senha com token invalido. {}", jwtUtil.getUsername (token));
            throw new ForbiddenException ("Faça login para acessar esta área.");
        }

        SystemUserEntity systemUserEntity = getSystemUser (token);

        if(!Objects.equals (systemUserEntity.getSaram (), authenticationDTO.saram ())){
            log.warn ("Tentativa de troca de senha. SARAM Enviado: {}, SARAM TOKEN: {}. SARAM token diferente SARAM enviado",
                    authenticationDTO.saram (),
                    systemUserEntity.getSaram ());
            throw new ForbiddenException ("SARAM do token invalido");
        }

        String correctPassword = systemUserEntity.getPassword ();
        if(!passwordEncoder.matches (authenticationDTO.password (), correctPassword)){
            log.warn ("Tentativa de troca de senha SARAM: {}, senha incorreta", systemUserEntity.getSaram ());
            throw new ForbiddenException ("Senha atual incorreta");
        }

        systemUserEntity.setPassword (passwordEncoder.encode (newPassword));

        try{
            systemUserRepository.save (systemUserEntity);
        } catch (RuntimeException e) {
            log.error ("Erro ao Atualizar senha do usuário {}: {}", systemUserEntity.getSaram (), e.getMessage ());
            throw new ServerException ("Erro ao atualizar senha.");
        }
    }


}
