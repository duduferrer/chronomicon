package bh.app.chronomicon.controller;

import bh.app.chronomicon.dto.AuthResponseDTO;
import bh.app.chronomicon.dto.AuthenticationDTO;
import bh.app.chronomicon.dto.RegisterSystemUserDTO;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.SystemUserEntity;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.repository.SystemUserRepository;
import bh.app.chronomicon.security.JwtUtil;
import bh.app.chronomicon.service.ShiftEventsService;
import bh.app.chronomicon.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "api/v1/auth")
@Tag(name = "Autenticação", description = "Operações relacionadas a criação e autenticação de usuários.")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(ShiftEventsService.class);


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SystemUserRepository systemUserRepository;
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Credenciais nao encontradas."),
    })
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken (
                authenticationDTO.saram (), authenticationDTO.password ());
        Authentication auth = authenticationManager.authenticate (usernamePassword);
        String token = jwtUtil.generateToken (authenticationDTO.saram ());
        return ResponseEntity.ok (new AuthResponseDTO (token));
    }

    @PostMapping("/admin/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar usuário de sistema"),
    })
    public ResponseEntity<String> register(@RequestBody @Valid RegisterSystemUserDTO registerSystemUserDTO){
        if(systemUserRepository.findUserBySaram (registerSystemUserDTO.saram ())!=null){
            log.warn ("Erro ao criar usuário, SARAM já cadastrado. SARAM: {}", registerSystemUserDTO.saram ());
            return ResponseEntity.status (HttpStatus.CONFLICT).body ("Já existe usuário com esse SARAM");
        }
        Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
        String firstPassword = registerSystemUserDTO.lpna ()+registerSystemUserDTO.lpna ();
        String encryptedPassword = passwordEncoder.encode (firstPassword);
        UserEntity userEntity = userService.findUser (registerSystemUserDTO.lpna ());
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
        } catch (RuntimeException e) {
            log.error ("Erro ao salvar novo usuário. {}", e.getMessage () );
            throw new ServerException ("Erro ao salvar usuário.");
        }
        URI location = URI.create("/api/v1/users/" + systemUserEntity.getId ());
        return ResponseEntity.created (location).build ();
    }

}
