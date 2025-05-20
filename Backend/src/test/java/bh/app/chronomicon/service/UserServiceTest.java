package bh.app.chronomicon.service;

import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.exception.ConflictException;
import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;
import bh.app.chronomicon.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    //    CREATE USER WITH ACTIVEUSER To be defined
    private UserEntity createUser(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
                                  boolean supervisor, boolean instructor, boolean trainee, boolean activeUser){
        UserEntity user = new UserEntity(rank, lpna_identifier, hierarchy, full_name, service_name, supervisor,
                instructor, trainee, activeUser);
        this.userRepository.save(user);
        return user;
    }
    //    CREATE USER WITH ACTIVEUSER TRUE
    private UserEntity createUser(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
                                  boolean supervisor, boolean instructor, boolean trainee){
        UserEntity user = new UserEntity(rank, lpna_identifier, hierarchy, full_name, service_name, supervisor,
                instructor, trainee, true);
        this.userRepository.save(user);
        return user;
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

         List<UserDTO> supsList = userService.findSupervisors();

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
        List<UserDTO> userList = userService.findUsers();

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

        List<UserDTO> instList = userService.findInstructors();

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

        List<UserDTO> traineesList = userService.findTrainees();

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

        List<UserDTO> operatorsList = userService.findOnlyOperators();

        assertEquals(3, operatorsList.size());
        assertEquals("Zoro", operatorsList.get(0).service_name());
        assertEquals("T. Chopper", operatorsList.get(1).service_name());
        assertEquals("Sanji", operatorsList.get(2).service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should find user passing ID as parameter")
    void findUserById() {
        UserEntity createdUser = createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);
        UserDTO foundUser = userService.findUserById(createdUser.getId());
        assertEquals("N. Uzumaki", foundUser.service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should throw exception due to LPNA already registered")
    void createNewUserSameLPNA() {
        createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);
        UserDTO userDTO = new UserDTO("ZZZZ", "Sasuke Uchiha", "Sasuke", Rank.MAJOR, (short) 0, false, false, false);
        assertThrows(ConflictException.class, ()->{
            userService.createNewUser(userDTO);
        } );

    }

    @Test
    @Transactional
    @DisplayName("Should create user successfully")
    void createNewUserSuccess() {
        UserDTO userDTO = new UserDTO("ZZZZ", "Sasuke Uchiha", "Sasuke", Rank.MAJOR, (short) 0, false, false, false);
        UserDTO newUser= userService.createNewUser(userDTO);
        assertEquals("Sasuke", newUser.service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should shift already existing users hierarchy and create a new user")
    void createNewUserSameHierarchy() {
        UserDTO user1DTO = new UserDTO("ZZZZ", "Rock Lee", "Lee", Rank.CAPITAO, (short) 2, false, false, false);

        UserDTO user0DTO = new UserDTO("AAAA", "Sasuke Uchiha", "Sasuke", Rank.MAJOR, (short) 2, false, false, false);
        userService.createNewUser(user1DTO);
        userService.createNewUser(user0DTO);
        userRepository.flush();
        entityManager.clear();
        UserDTO foundUser0 = userService.findUserByLPNA("AAAA");
        UserDTO foundUser1 = userService.findUserByLPNA("ZZZZ");


        assertEquals(2, foundUser0.hierarchy());
        assertEquals(3, foundUser1.hierarchy());

    }



    @Test
    @Transactional
    @DisplayName("Should find user passing LPNA as parameter")
    void findUserByLPNASuccess() {
         createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);
         UserDTO user = userService.findUserByLPNA("ZZZZ");
        assertEquals("N. Uzumaki", user.service_name());
    }

    @Test
    @Transactional
    @DisplayName("Should return exception when searching user passing LPNA as parameter")
    void findUserByLPNAFail() {
        createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);

        assertThrows(NotFoundException.class, ()->{
            userService.findUserByLPNA("AAAA");
        });
    }

    @Test
    @Transactional
    @DisplayName("Should return exception when searching user passing ID as parameter")
    void findUserByIdFail() {
        UserEntity user = createUser(Rank.CAPITAO,  "ZZZZ", (short) 2, "Naruto Uzumaki", "N. Uzumaki",
                false, true, false);

        assertThrows(NotFoundException.class, ()->{
            userService.findUserById(user.getId()+1);
        });
    }

    @Test
    @Transactional
    @DisplayName("Should activate an inactive user")
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
        UserEntity user = userRepository.findUserByLPNA("FFFF");
        boolean userStatus = user.isActive();
        short hierarchy = user.getHierarchy();
        assertTrue(userStatus);
        assertEquals(4, hierarchy);
    }

    @Test
    @Transactional
    @DisplayName("Should deactivate an active user")
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
        UserEntity user = userRepository.findUserByLPNA("BBBB");
        boolean userStatus = user.isActive();
        short hierarchy = user.getHierarchy();
        assertFalse(userStatus);
        assertEquals(1001, hierarchy);
    }
    @Test
    @Transactional
    @DisplayName("Should return exception when trying to activate an active user")
    void activateUserException() {
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
    @DisplayName("Should return exception when trying to deactivate an inactive user")
    void deactivateUserException() {
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
            userService.deactivateUser("GGGG");
        });
    }
}