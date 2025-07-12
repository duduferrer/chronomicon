package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.*;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.entities.CoreUserInformationEntity;
import bh.app.chronomicon.model.enums.Rank;
import bh.app.chronomicon.model.enums.Role;
import bh.app.chronomicon.repository.AtcoRepository;
import bh.app.chronomicon.repository.CoreUserInformationRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private AtcoService userService;

    @Autowired
    private AtcoRepository atcoRepository;

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private CoreUserInformationRepository coreUserInformationRepository;
    
//    @MockitoBean
//    private AuthService authService;


    //    CREATE USER WITH ACTIVEUSER To be defined
    private AtcoEntity createUser(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
								  boolean supervisor, boolean instructor, boolean trainee, boolean activeUser){
        CoreUserInformationEntity coreUserInformation = new CoreUserInformationEntity();
        coreUserInformation.setRank(rank);
        coreUserInformation.setFullName(full_name);
        coreUserInformation.setService_name(service_name);
        coreUserInformationRepository.save(coreUserInformation);
        AtcoEntity atco = new AtcoEntity();
        atco.setSupervisor(supervisor);
        atco.setInstructor(instructor);
        atco.setTrainee(trainee);
        atco.setHierarchy(hierarchy);
        atco.setLpna_identifier(lpna_identifier);
        atco.setActive(activeUser);
        atco.setCoreUserInformationEntity(coreUserInformation);
        AtcoEntity savedAtco = atcoRepository.save(atco);
        return savedAtco;
    }
    //    CREATE USER WITH ACTIVEUSER TRUE
    private AtcoEntity createUser(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
								  boolean supervisor, boolean instructor, boolean trainee){
        CoreUserInformationEntity coreUserInformation = new CoreUserInformationEntity();
        coreUserInformation.setRank(rank);
        coreUserInformation.setFullName(full_name);
        coreUserInformation.setService_name(service_name);
        coreUserInformationRepository.save(coreUserInformation);
        AtcoEntity atco = new AtcoEntity();
        atco.setSupervisor(supervisor);
        atco.setInstructor(instructor);
        atco.setTrainee(trainee);
        atco.setHierarchy(hierarchy);
        atco.setLpna_identifier(lpna_identifier);
        atco.setCoreUserInformationEntity(coreUserInformation);
        AtcoEntity savedAtco = atcoRepository.save(atco);
        return savedAtco;
    }

    @Test
    @Transactional
    @DisplayName("Should List all active supervisors ordered by hierarchy")
    void findSupervisors() {
        createUser(Rank.CAPITAO, "AAAA", (short) 0, "Monkey Luffy", "Luffy",
                true, true, false);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Zoro", "Zoro",
                false, false, true);
        createUser(Rank.TERCEIRO_SGT, "CCCC", (short) 3, "Sanji", "Sanji",
                true, true, true);
        createUser(Rank.CAPITAO,  "DDDD", (short) 2, "Tony Chopper", "T. Chopper",
                true, false, true);
        createUser(Rank.SEGUNDO_SGT,  "EEEE", (short) 4, "Gol D Roger", "G.D. Roger",
                true, false, true, false);

         List<AtcoDTO> supsList = userService.findSupervisors();

         assertEquals(3, supsList.size());
         assertEquals("Luffy", supsList.get(0).service_name());
         assertEquals("T. Chopper", supsList.get(1).service_name());
         assertEquals("Sanji", supsList.get(2).service_name());


    }

    @Test
    @Transactional
    @DisplayName("Should List all active users ordered by hierarchy")
    void findUsers() {
        createUser(Rank.CAPITAO, "AAAA", (short) 0, "Monkey Luffy", "Luffy",
                true, true, false);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Zoro", "Zoro",
                false, false, true);
        createUser(Rank.TERCEIRO_SGT, "CCCC", (short) 3, "Sanji", "Sanji",
                true, true, true);
        createUser(Rank.CAPITAO,  "DDDD", (short) 2, "Tony Chopper", "T. Chopper",
                true, false, true);
        createUser(Rank.SEGUNDO_SGT,  "EEEE", (short) 4, "Gol D Roger", "G.D. Roger",
                true, false, true, false);
        List<AtcoDTO> userList = userService.findUsers();

        assertEquals(4, userList.size());
        assertEquals("Luffy", userList.get(0).service_name());
        assertEquals("Zoro", userList.get(1).service_name());
        assertEquals("T. Chopper", userList.get(2).service_name());
        assertEquals("Sanji", userList.get(3).service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should List all active instructors ordered by hierarchy")
    void findInstructors() {
        createUser(Rank.CAPITAO, "AAAA", (short) 0, "Monkey Luffy", "Luffy",
                true, true, false);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 3, "Zoro", "Zoro",
                false, true, true);
        createUser(Rank.TERCEIRO_SGT, "CCCC", (short) 1, "Sanji", "Sanji",
                true, true, true);
        createUser(Rank.CAPITAO,  "DDDD", (short) 2, "Tony Chopper", "T. Chopper",
                true, false, true);
        createUser(Rank.SEGUNDO_SGT,  "EEEE", (short) 4, "Gol D Roger", "G.D. Roger",
                true, true, true, false);

        List<AtcoDTO> instList = userService.findInstructors();

        assertEquals(3, instList.size());
        assertEquals("Luffy", instList.get(0).service_name());
        assertEquals("Sanji", instList.get(1).service_name());
        assertEquals("Zoro", instList.get(2).service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should List all active trainees ordered by hierarchy")
    void findTrainees() {
        createUser(Rank.CAPITAO, "AAAA", (short) 0, "Monkey Luffy", "Luffy",
                true, true, false);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Zoro", "Zoro",
                false, false, true);
        createUser(Rank.TERCEIRO_SGT, "CCCC", (short) 3, "Sanji", "Sanji",
                true, true, true);
        createUser(Rank.CAPITAO,  "DDDD", (short) 2, "Tony Chopper", "T. Chopper",
                true, false, true);
        createUser(Rank.SEGUNDO_SGT,  "EEEE", (short) 4, "Gol D Roger", "G.D. Roger",
                true, false, true, false);

        List<AtcoDTO> traineesList = userService.findTrainees();

        assertEquals(3, traineesList.size());
        assertEquals("Zoro", traineesList.get(0).service_name());
        assertEquals("T. Chopper", traineesList.get(1).service_name());
        assertEquals("Sanji", traineesList.get(2).service_name());

    }

    @Test
    @Transactional
    @DisplayName("Should List all active ONLY operators ordered by hierarchy")
    void findOnlyOperators() {
        createUser(Rank.CAPITAO, "AAAA", (short) 0, "Monkey Luffy", "Luffy",
                true, false, false);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Zoro", "Zoro",
                false, false, false);
        createUser(Rank.TERCEIRO_SGT, "CCCC", (short) 3, "Sanji", "Sanji",
                false, false, false);
        createUser(Rank.CAPITAO,  "DDDD", (short) 2, "Tony Chopper", "T. Chopper",
                false, false, false);
        createUser(Rank.SEGUNDO_SGT,  "EEEE", (short) 4, "Gol D Roger", "G.D. Roger",
                false, false, false, false);

        List<AtcoDTO> operatorsList = userService.findOnlyOperators();

        assertEquals(3, operatorsList.size());
        assertEquals("Zoro", operatorsList.get(0).service_name());
        assertEquals("T. Chopper", operatorsList.get(1).service_name());
        assertEquals("Sanji", operatorsList.get(2).service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should find user passing ID as parameter")
    void findUserById() {
        AtcoEntity createdUser = createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);
        AtcoDTO foundUser = userService.findUserById(createdUser.getId());
        assertEquals("N. Uzumaki", foundUser.service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception due to LPNA already registered")
    void createNewUserSameLPNA() {
        createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);
        CoreUserInformationDTO coreUserInformationDTO = new CoreUserInformationDTO("teste@teste.com", "1234567", "Sasuke Uchiha", "Sasuke", Rank.MAJOR, "00000000000");
        CreateAtcoDTO createAtcoDTO = new CreateAtcoDTO("ZZZZ", false, false, false);
        CreateSystemUserDTO createSystemUserDTO = new CreateSystemUserDTO(Role.USER);
        CreateUserDTO userDTO = new CreateUserDTO(coreUserInformationDTO, createAtcoDTO,createSystemUserDTO);
        assertThrows(ConflictException.class, ()->{
            userService.createNewUser(userDTO);
        } );

    }

    @Test
    @Transactional
    @DisplayName("Should create user successfully")
    void createNewUserSuccess() {
        CoreUserInformationDTO coreUserInformationDTO = new CoreUserInformationDTO("teste@teste.com", "1234567", "Sasuke Uchiha", "Sasuke", Rank.MAJOR, "00000000000");
        CreateAtcoDTO createAtcoDTO = new CreateAtcoDTO("ZZZZ", false, false, false);
        CreateSystemUserDTO createSystemUserDTO = new CreateSystemUserDTO(Role.USER);
        CreateUserDTO userDTO = new CreateUserDTO(coreUserInformationDTO, createAtcoDTO,createSystemUserDTO);
        assertEquals("Sasuke", userDTO.coreUserInformationDTO().serviceName());
    }

    @Test
    @Transactional
    @DisplayName("Should shift already existing users hierarchy and create a new user")
    @WithMockUser(username = "SARAM_TEST")
    void createNewUserSameRank() {
        CoreUserInformationDTO coreUserInformationDTO = new CoreUserInformationDTO("teste@teste.com", "1234567", "Sasuke Uchiha", "Sasuke", Rank.MAJOR, "00000000000");
        CreateAtcoDTO createAtcoDTO = new CreateAtcoDTO("ZZZZ", false, false, false);
        CreateSystemUserDTO createSystemUserDTO = new CreateSystemUserDTO(Role.USER);
        CreateUserDTO userDTO = new CreateUserDTO(coreUserInformationDTO, createAtcoDTO,createSystemUserDTO);
        CoreUserInformationDTO coreUserInformationDTO1 = new CoreUserInformationDTO("teste1@teste.com", "1234568", "Rock Lee", "Lee", Rank.MAJOR, "00000000000");
        CreateAtcoDTO createAtcoDTO1 = new CreateAtcoDTO("AAAA", false, false, false);
        CreateSystemUserDTO createSystemUserDTO1 = new CreateSystemUserDTO(Role.USER);
        CreateUserDTO userDTO1 = new CreateUserDTO(coreUserInformationDTO1, createAtcoDTO1,createSystemUserDTO1);
        userService.createNewUser(userDTO);
        userService.createNewUser(userDTO1);
        atcoRepository.flush();
        entityManager.clear();
        AtcoDTO foundUser0 = userService.findUserByLPNAReturnDTO ("ZZZZ");
        AtcoDTO foundUser1 = userService.findUserByLPNAReturnDTO ("AAAA");


        assertEquals(0, foundUser0.hierarchy());
        assertEquals(1, foundUser1.hierarchy());

    }



    @Test
    @Transactional
    @DisplayName("Should find user passing LPNA as parameter")
    void findUserByLPNAReturnDTOSuccess() {
         createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);
         AtcoDTO user = userService.findUserByLPNAReturnDTO ("ZZZZ");
        assertEquals("N. Uzumaki", user.service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should return exception when searching user passing LPNA as parameter")
    void findUserByLPNAReturnDTOFail() {
        createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);

        assertThrows(NotFoundException.class, ()->{
            userService.findUserByLPNAReturnDTO ("AAAA");
        });
    }

    @Test
    @Transactional
    @DisplayName("Should return exception when searching user passing ID as parameter")
    void findUserByIdFail() {
        AtcoEntity user = createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);

        assertThrows(NotFoundException.class, ()->{
            userService.findUserById(user.getId()+1);
        });
    }

    @Test
    @Transactional
    @DisplayName("Should activate an inactive user")
    @WithMockUser(username = "SARAM_TEST")
    void activateUser() {
        createUser(Rank.SUBOFICIAL, "AAAA", (short) 0, "Son Goku", "Goku",
                true, true, false);
        createUser(Rank.PRIMEIRO_SGT,  "BBBB", (short) 1, "Vegeta", "Vegeta",
                false, true, true);
        createUser(Rank.SEGUNDO_SGT, "CCCC", (short) 2, "Picclo", "Piccolo",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "DDDD", (short) 3, "Kuririn", "Kuririn",
                true, false, true);
        createUser(Rank.TERCEIRO_SGT,  "EEEE", (short) 4, "Son Gohan", "Gohan",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "FFFF", (short) 1001, "Yamcha", "Yamcha",
                true, true, true, false);
        createUser(Rank.SUBOFICIAL,  "GGGG", (short) 1002, "Mestre Kame", "Mestre Kame",
                true, true, true, false);
        userService.activateUser("FFFF");
        entityManager.clear();
        AtcoEntity user = atcoRepository.findUserByLPNA("FFFF");
        boolean userStatus = user.isActive();
        short hierarchy = user.getHierarchy();
        assertTrue(userStatus);
        assertEquals(4, hierarchy);
    }

    @Test
    @Transactional
    @DisplayName("Should deactivate an active user")
    @WithMockUser(username = "SARAM_TEST")
    void deactivateUser() {
        createUser(Rank.SUBOFICIAL, "AAAA", (short) 0, "Son Goku", "Goku",
                true, true, false);
        createUser(Rank.PRIMEIRO_SGT,  "BBBB", (short) 1, "Vegeta", "Vegeta",
                false, true, true);
        createUser(Rank.SEGUNDO_SGT, "CCCC", (short) 2, "Picclo", "Piccolo",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "DDDD", (short) 3, "Kuririn", "Kuririn",
                true, false, true);
        createUser(Rank.TERCEIRO_SGT,  "EEEE", (short) 4, "Son Gohan", "Gohan",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "FFFF", (short) 1001, "Yamcha", "Yamcha",
                true, true, true, false);
        createUser(Rank.SUBOFICIAL,  "GGGG", (short) 1002, "Mestre Kame", "Mestre Kame",
                true, true, true, false);
        userService.deactivateUser("BBBB");
        entityManager.clear();
        AtcoEntity user = atcoRepository.findUserByLPNA("BBBB");
        boolean userStatus = user.isActive();
        short hierarchy = user.getHierarchy();
        assertFalse(userStatus);
        assertEquals(1001, hierarchy);
    }
    @Test
    @Transactional
    @DisplayName("Should return exception when trying to activate an active user")
    void activateUserAlreadyActive() {
        createUser(Rank.SUBOFICIAL, "AAAA", (short) 0, "Son Goku", "Goku",
                true, true, false);
        createUser(Rank.PRIMEIRO_SGT,  "BBBB", (short) 1, "Vegeta", "Vegeta",
                false, true, true);
        createUser(Rank.SEGUNDO_SGT, "CCCC", (short) 2, "Picclo", "Piccolo",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "DDDD", (short) 3, "Kuririn", "Kuririn",
                true, false, true);
        createUser(Rank.TERCEIRO_SGT,  "EEEE", (short) 4, "Son Gohan", "Gohan",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "FFFF", (short) 1001, "Yamcha", "Yamcha",
                true, true, true, false);
        createUser(Rank.SUBOFICIAL,  "GGGG", (short) 1002, "Mestre Kame", "Mestre Kame",
                true, true, true, false);

        assertThrows(ConflictException.class, ()->{
            userService.activateUser("AAAA");
        });
    }

    @Test
    @Transactional
    @DisplayName("Should return exception when trying to activate inexistent user")
    void activateUserExceptionUserNotFound() {
        createUser(Rank.SUBOFICIAL, "AAAA", (short) 0, "Son Goku", "Goku",
                true, true, false);
        createUser(Rank.PRIMEIRO_SGT,  "BBBB", (short) 1, "Vegeta", "Vegeta",
                false, true, true);
        createUser(Rank.SEGUNDO_SGT, "CCCC", (short) 2, "Picclo", "Piccolo",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "DDDD", (short) 3, "Kuririn", "Kuririn",
                true, false, true);
        createUser(Rank.TERCEIRO_SGT,  "EEEE", (short) 4, "Son Gohan", "Gohan",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "FFFF", (short) 1001, "Yamcha", "Yamcha",
                true, true, true, false);
        createUser(Rank.SUBOFICIAL,  "GGGG", (short) 1002, "Mestre Kame", "Mestre Kame",
                true, true, true, false);
        assertThrows(NotFoundException.class, ()->{
            userService.activateUser("ZZZZ");
        });

    }

    @Test
    @Transactional
    @DisplayName("Should return exception when trying to deactivate inexistent user")
    void deactivateUserExceptionUserNotFound() {
        createUser (Rank.SUBOFICIAL, "AAAA", (short) 0, "Son Goku", "Goku",
                true, true, false);
        createUser (Rank.PRIMEIRO_SGT, "BBBB", (short) 1, "Vegeta", "Vegeta",
                false, true, true);
        createUser (Rank.SEGUNDO_SGT, "CCCC", (short) 2, "Picclo", "Piccolo",
                true, true, true);
        createUser (Rank.SEGUNDO_SGT, "DDDD", (short) 3, "Kuririn", "Kuririn",
                true, false, true);
        createUser (Rank.TERCEIRO_SGT, "EEEE", (short) 4, "Son Gohan", "Gohan",
                true, true, true);
        createUser (Rank.SEGUNDO_SGT, "FFFF", (short) 1001, "Yamcha", "Yamcha",
                true, true, true, false);
        createUser (Rank.SUBOFICIAL, "GGGG", (short) 1002, "Mestre Kame", "Mestre Kame",
                true, true, true, false);
        assertThrows (NotFoundException.class, () -> {
            userService.deactivateUser ("ZZZZ");
        });
    }
    @Test
    @Transactional
    @DisplayName("Should activate an inactive user when user list is empty")
    @WithMockUser(username = "SARAM_TEST")
    void activateUserWhenListIsEmpty() {
        createUser(Rank.SUBOFICIAL, "AAAA", (short) 1005, "Son Goku", "Goku",
                true, true, false, false);
        userService.activateUser("AAAA");
        entityManager.clear();
        AtcoEntity user = atcoRepository.findUserByLPNA("AAAA");
        boolean userStatus = user.isActive();
        short hierarchy = user.getHierarchy();
        assertTrue(userStatus);
        assertEquals(0, hierarchy);
    }

    @Test
    @Transactional
    @DisplayName("Should update user with provided data and don't change unprovided data")
    @WithMockUser(username = "SARAM_TEST")
    void updateUser() {
        createUser(Rank.SUBOFICIAL, "AAAA", (short) 0, "Tanjiro Kamado", "Tanjiro",
                true, true, false, true);
        UpdateUserDTO updateDto = new UpdateUserDTO ("GGGG", null, null, "Kamado",
                null, null,null);
        userService.updateUser ("AAAA", updateDto);
        atcoRepository.flush ();
        entityManager.clear ();
        AtcoDTO user = userService.findUserByLPNAReturnDTO ("GGGG");
        assertEquals ("Kamado", user.service_name ());
        assertEquals (Rank.SUBOFICIAL, user.rank ());
    }
}