package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.AuthenticationDTO;
import bh.app.chronomicon.dto.RegisterSystemUserDTO;
import bh.app.chronomicon.dto.UpdatePasswordDTO;
import bh.app.chronomicon.dto.AtcoDTO;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.ForbiddenException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.SystemUserEntity;
import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.enums.Role;
import bh.app.chronomicon.repository.SystemUserRepository;
import bh.app.chronomicon.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    SystemUserRepository systemUserRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return systemUserRepository.findUserBySaram (username);
    }

    public AtcoDTO getUser(String token){
        String saram = jwtUtil.getUsername(token);
        AtcoEntity user = systemUserRepository.findUserBySaram (saram).getAtco();
        return new AtcoDTO(user);
    }

    public SystemUserEntity getSystemUser(String token){
        String saram = jwtUtil.getUsername (token);
        return systemUserRepository.findUserBySaram (saram);
    }

    public SystemUserEntity getUserByEmail(String email) throws UsernameNotFoundException{
        return Optional.ofNullable(systemUserRepository.findUserByEmailAddress(email))
                .orElseThrow(() -> {
                    log.warn("Tentativa de recuperar senha do email {} fracassada. Usuário não encontrado.", email);
                    return new UsernameNotFoundException("Usuário não encontrado.");
                });
    }

    public void updateLastLogin(String token){
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        SystemUserEntity systemUserEntity = getSystemUser (token);
        systemUserEntity.setLast_login (now);
        try{
            systemUserRepository.save (systemUserEntity);
        }catch(RuntimeException e){
            log.error ("Erro ao atualizar hora do ultimo login {}, Hora do ultimo login: {}, Erro: {}",
                    systemUserEntity.getCoreUserInformation().getSaram(),
                    now,
                    e.getMessage ());
            throw new ServerException ("Erro ao atualizar hora do ultimo login");
        }
    }

    public void updatePassword(String token, AuthenticationDTO authenticationDTO, UpdatePasswordDTO updatePasswordDTO){

        if(!jwtUtil.isTokenValid (token)){
            log.warn ("Tentativa de troca de senha com token invalido. {}", jwtUtil.getUsername (token));
            throw new ForbiddenException ("Faça login para acessar esta área.");
        }

        SystemUserEntity systemUserEntity = getSystemUser (token);

        if(!Objects.equals (systemUserEntity.getCoreUserInformation().getSaram(), authenticationDTO.saram ())){
            log.warn ("Tentativa de troca de senha. SARAM Enviado: {}, SARAM TOKEN: {}. SARAM token diferente SARAM enviado",
                    authenticationDTO.saram (),
                    systemUserEntity.getCoreUserInformation().getSaram());
            throw new ForbiddenException ("SARAM do token invalido");
        }

        String correctPassword = systemUserEntity.getPassword ();
        if(!passwordEncoder.matches (authenticationDTO.password (), correctPassword)){
            log.warn ("Tentativa de troca de senha SARAM: {}, senha incorreta", systemUserEntity.getCoreUserInformation().getSaram());
            throw new ForbiddenException ("Senha atual incorreta");
        }

        systemUserEntity.setPassword (passwordEncoder.encode (updatePasswordDTO.newPassword ()));

        try{
            systemUserRepository.save (systemUserEntity);
        } catch (RuntimeException e) {
            log.error ("Erro ao Atualizar senha do usuário {}: {}", systemUserEntity.getCoreUserInformation().getSaram(), e.getMessage ());
            throw new ServerException ("Erro ao atualizar senha.");
        }
    }

    public String extractToken(HttpServletRequest request){
        String authHeader = request.getHeader ("Authorization");
        if(authHeader==null || !authHeader.startsWith ("Bearer ")){
            return null;
        }else{
            return authHeader.replace ("Bearer ","");
        }
    }
    
    public SystemUserEntity registerSystemUser(RegisterSystemUserDTO registerSystemUserDTO, String token){
        if(systemUserRepository.findUserBySaram (registerSystemUserDTO.saram ())!=null){
            log.warn ("Erro ao criar usuário, SARAM já cadastrado. SARAM: {}", registerSystemUserDTO.saram ());
            throw new ConflictException("Já existe usuário com esse SARAM");
        }
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        String firstPassword = registerSystemUserDTO.lpna ()+registerSystemUserDTO.lpna ();
        String encryptedPassword = passwordEncoder.encode (firstPassword);
        AtcoEntity userEntity = userService.findUser (registerSystemUserDTO.lpna ());
        SystemUserEntity systemUserEntity = new SystemUserEntity (
                registerSystemUserDTO.role (),
                userEntity,
                now,
                now,
                true,
                null,
                encryptedPassword,
                false,
                false,
                false,
                registerSystemUserDTO.emailAddress (),
                registerSystemUserDTO.saram ()
        );
        try {
            systemUserRepository.save (systemUserEntity);
            log.info("Usuário {} criado com sucesso. Por: {}", systemUserEntity.getId(), getSystemUser(token).getUsername());
        } catch (RuntimeException e) {
            log.error ("Erro ao salvar novo usuário. {}", e.getMessage () );
            throw new ServerException ("Erro ao salvar usuário.");
        }
        return systemUserEntity;
    }
    
    public Role getAuthenticatedUserRole() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof SystemUserEntity) {
            SystemUserEntity userEntity = (SystemUserEntity) authentication.getPrincipal();
            return userEntity.getRole();
        }
        return null;
        
    }

}
