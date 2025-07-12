package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.CreateUserDTO;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.entities.CoreUserInformationEntity;
import bh.app.chronomicon.model.entities.SystemUserEntity;
import bh.app.chronomicon.repository.CoreUserInformationRepository;
import bh.app.chronomicon.repository.SystemUserRepository;
import bh.app.chronomicon.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
public class SystemUserService {
	@Autowired
	SystemUserRepository systemUserRepository;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	CoreUserInformationRepository coreUserInformationRepository;
	@Autowired
	AuthService authService;
	@Autowired
	AtcoService atcoService;
	
	private static final Logger log = LoggerFactory.getLogger(AuthService.class);
	
	public SystemUserEntity registerSystemUser(CreateUserDTO createUserDTO){
		if(systemUserRepository.findUserBySaram (createUserDTO.coreUserInformationDTO().saram())!=null){
			log.warn ("Erro ao criar usuário, SARAM já cadastrado. SARAM: {}", createUserDTO.coreUserInformationDTO().saram());
			throw new ConflictException("Já existe usuário com esse SARAM");
		}
		Timestamp now = Timestamp.valueOf (LocalDateTime.now ());
		String serviceName = createUserDTO.coreUserInformationDTO().serviceName().toLowerCase().split(" ",2)[0];
		String firstPassword = serviceName + createUserDTO.coreUserInformationDTO().saram();
		String encryptedPassword = passwordEncoder.encode (firstPassword);
		CoreUserInformationEntity coreUserInformationEntity = authService.createCoreUser(createUserDTO);
		AtcoEntity atcoEntity = atcoService.createNewUser(createUserDTO);
		SystemUserEntity systemUserEntity = new SystemUserEntity ();
		systemUserEntity.setPassword(encryptedPassword);
		systemUserEntity.setCoreUserInformation(coreUserInformationEntity);
		systemUserEntity.setRole(createUserDTO.createSystemUserDTO().role());
		systemUserEntity.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
		systemUserEntity.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
		systemUserEntity.setAtco(atcoEntity);
		try {
			systemUserRepository.save (systemUserEntity);
			log.info("Usuário {} criado com sucesso. Por: {}", systemUserEntity.getId(), authService.getActiveUserUsername());
		} catch (RuntimeException e) {
			log.error ("Erro ao salvar novo usuário. {}", e.getMessage () );
			throw new ServerException("Erro ao salvar usuário.");
		}
		return systemUserEntity;
	}
}
