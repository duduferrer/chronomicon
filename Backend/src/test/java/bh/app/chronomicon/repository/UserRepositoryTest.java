package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository repository;
    //    CREATE USER WITH ACTIVEUSER To be defined
    private UserEntity createUser(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
                                  boolean supervisor, boolean instructor, boolean trainee, boolean activeUser){
        UserEntity user = new UserEntity(rank, lpna_identifier, hierarchy, full_name, service_name, supervisor,
                instructor, trainee, activeUser);
        this.entityManager.persist(user);
        return user;
    }
    //    CREATE USER WITH ACTIVEUSER TRUE
    private UserEntity createUser(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
                                  boolean supervisor, boolean instructor, boolean trainee){
        UserEntity user = new UserEntity(rank, lpna_identifier, hierarchy, full_name, service_name, supervisor,
                instructor, trainee, true);
        this.entityManager.persist(user);
        return user;
    }



    @Test
    @DisplayName("Search if there is already a user created with this LPNA and return true")
    void existsByLpnaIdentifierSuccess() {
        createUser(Rank.TERCEIRO_SGT, "AAAA", (short) 0, "Bruce Wayne", "Wayne",
                true, true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Clark Kent", "Kent",
                true, false, true, true);

        boolean result = repository.existsByLpnaIdentifier("AAAA");
        assertTrue(result);
    }
    @Test
    @DisplayName("Search if there is already a user created with this LPNA and return false")
    void existsByLpnaIdentifierFail() {
        createUser(Rank.TERCEIRO_SGT, "AAAA", (short) 0, "Bruce Wayne", "Wayne",
                true, true, true);
        createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Clark Kent", "Kent",
                true, false, true);
        boolean result = repository.existsByLpnaIdentifier("PPIA");
        assertFalse(result);
    }

    @Test
    @DisplayName("Should get a list of active users that are supervisors and return ordered by hierarchy")
    void findSupsOrderByHierarchy() {
        createUser(Rank.TERCEIRO_SGT, "PPPP", (short) 8, "Kenny", "Kenny",
                true, false, true, false);
        createUser(Rank.TERCEIRO_SGT,  "GGGG", (short) 6, "Eric Cartman", "Cartman",
                true, false, false);
        createUser(Rank.TERCEIRO_SGT, "HHHH", (short) 7, "Stanley Marsh", "Stanley Marsh",
                false, false, true);
        createUser(Rank.TERCEIRO_SGT,  "AABB", (short) 0, "Kyle Broflovsky", "Kyle",
                true, true, true);
        createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 2, "Butters", "Butters",
                true, true, false);
        List<UserEntity> result =  repository.findSupsOrderByHierarchy();
        assertEquals(3, result.size());
        assertEquals("Kyle", result.get(0).getService_name());
        assertEquals("Butters", result.get(1).getService_name());
        assertEquals("Cartman", result.get(2).getService_name());
    }

    @Test
    @DisplayName("Should get a list of active users and return ordered by hierarchy")
    void findActiveUsersOrderByHierarchy() {
        createUser(Rank.TERCEIRO_SGT, "PPPP", (short) 8, "Kenny", "Kenny",
                true, false, true, false);
        createUser(Rank.TERCEIRO_SGT,  "GGGG", (short) 6, "Eric Cartman", "Cartman",
                true, false, false);
        createUser(Rank.TERCEIRO_SGT, "HHHH", (short) 7, "Stanley Marsh", "Stanley Marsh",
                false, false, true);
        createUser(Rank.TERCEIRO_SGT,  "AABB", (short) 0, "Kyle Broflovsky", "Kyle",
                true, false, false);
        List<UserEntity> result = repository.findActiveUsersOrderByHierarchy();
        assertEquals(3, result.size());
        assertEquals("Kyle", result.get(0).getService_name());
        assertEquals("Cartman", result.get(1).getService_name());
        assertEquals("Stanley Marsh", result.get(2).getService_name());
    }

    @Test
    @DisplayName("Should get a list of active users that are instructors and return ordered by hierarchy")
    void findInstsOrderByHierarchy() {
        createUser(Rank.TERCEIRO_SGT, "PPPP", (short) 8, "Kenny", "Kenny",
                true, true, true, false);
        createUser(Rank.TERCEIRO_SGT,  "GGGG", (short) 6, "Eric Cartman", "Cartman",
                true, true, false);
        createUser(Rank.TERCEIRO_SGT, "HHHH", (short) 7, "Stanley Marsh", "Stanley Marsh",
                false, true, true);
        createUser(Rank.TERCEIRO_SGT,  "AABB", (short) 0, "Kyle Broflovsky", "Kyle",
                true, false, false);
        createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 2, "Butters", "Butters",
                true, true, false);
        List<UserEntity> result = repository.findInstsOrderByHierarchy();
        assertEquals(3, result.size());
        assertEquals("Butters", result.get(0).getService_name());
        assertEquals("Cartman", result.get(1).getService_name());
        assertEquals("Stanley Marsh", result.get(2).getService_name());
    }

    @Test
    @DisplayName("Should get a list of active users that are trainees and return ordered by hierarchy")
    void findTraineesOrderByHierarchy() {
        createUser(Rank.TERCEIRO_SGT, "PPPP", (short) 8, "Kenny", "Kenny",
                true, true, true, false);
        createUser(Rank.TERCEIRO_SGT,  "GGGG", (short) 6, "Eric Cartman", "Cartman",
                false, true, true);
        createUser(Rank.TERCEIRO_SGT, "HHHH", (short) 7, "Stanley Marsh", "Stanley Marsh",
                true, true, true);
        createUser(Rank.TERCEIRO_SGT,  "AABB", (short) 0, "Kyle Broflovsky", "Kyle",
                true, false, false);
        createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 2, "Butters", "Butters",
                true, false, true);
        List<UserEntity> result = repository.findTraineesOrderByHierarchy();
        assertEquals(3, result.size());
        assertEquals("Butters", result.get(0).getService_name());
        assertEquals("Cartman", result.get(1).getService_name());
        assertEquals("Stanley Marsh", result.get(2).getService_name());
    }

    @Test
    @DisplayName("Should get a list of active users that are ONLY operators and return ordered by hierarchy")
    void findOnlyOpsOrderByHierarchy() {
        createUser(Rank.TERCEIRO_SGT, "PPPP", (short) 8, "Kenny", "Kenny",
                true, true, true, false);
        createUser(Rank.TERCEIRO_SGT,  "GGGG", (short) 6, "Eric Cartman", "Cartman",
                false, false, false);
        createUser(Rank.TERCEIRO_SGT, "HHHH", (short) 7, "Stanley Marsh", "Stanley Marsh",
                false, false, false);
        createUser(Rank.TERCEIRO_SGT,  "AABB", (short) 0, "Kyle Broflovsky", "Kyle",
                true, false, false);
        createUser(Rank.TERCEIRO_SGT,  "ACBB", (short) 3, "Clyde", "Clyde",
                true, true, true);
        createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 2, "Butters", "Butters",
                false, false, false);
        List<UserEntity> result = repository.findOnlyOpsOrderByHierarchy();
        assertEquals(3, result.size());
        assertEquals("Butters", result.get(0).getService_name());
        assertEquals("Cartman", result.get(1).getService_name());
        assertEquals("Stanley Marsh", result.get(2).getService_name());
    }

    @Test
    @DisplayName("Should get a user by userID")
    void findUserById() {
         UserEntity user = createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 2, "Butters", "Butters",
                false, false, false);
         Long userID = user.getId();
         UserEntity result = repository.findUserById(userID);
         assertEquals("Butters", result.getService_name());
    }

    @Test
    @DisplayName("Should get a user by lpna identifier")
    void findUserByLPNA() {
        UserEntity user = createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 2, "Butters", "Butters",
                false, false, false);
        String userLPNA = user.getLpna_identifier();
        UserEntity result = repository.findUserByLPNA(userLPNA);
        assertEquals("Butters", result.getService_name());
    }

    @Test
    @DisplayName("Search if there is already a user created with this Hierarchy and return true")
    void existsByHierarchySuccess() {
        UserEntity user = createUser(Rank.TERCEIRO_SGT, "AAAA", (short) 0, "Bruce Wayne", "Wayne",
                true, true, true, true);

        boolean result = repository.existsByHierarchy(user.getHierarchy());
        assertTrue(result);
    }
    @Test
    @DisplayName("Search if there is already a user created with this Hierarchy and return false")
    void existsByHierarchyFail() {
        UserEntity user = createUser(Rank.SEGUNDO_SGT,  "BBBB", (short) 1, "Clark Kent", "Kent",
                true, false, true);
        boolean result = repository.existsByHierarchy((short)0);
        assertFalse(result);
    }

    @Test
    @DisplayName("Shift hierarchy of all users between target and 999")
    void shiftActiveUsersHierarchy() {
        UserEntity user1 = createUser(Rank.CAPITAO,  "AAAA", (short) 1, "Bruce Banner", "Hulk",
                true, false, true);
        UserEntity user2 = createUser(Rank.TERCEIRO_SGT,  "BBBB", (short) 2, "John Stewart", "Lanterna Verde",
                true, false, true);
        UserEntity user3 = createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 3, "Barry Allen", "Flash",
                true, false, true);
        UserEntity user4 = createUser(Rank.SEGUNDO_SGT,  "DDDD", (short) 1000, "Chapolin Colorado", "Chapolin Colorado",
                true, false, true);
        repository.shiftActiveUsersHierarchy(user2.getHierarchy());
        entityManager.flush();
        entityManager.clear();
        List<UserEntity> users = repository.findActiveUsersOrderByHierarchy();
        assertEquals(1, users.get(0).getHierarchy());
        assertEquals(3, users.get(1).getHierarchy());
        assertEquals(4, users.get(2).getHierarchy());
        assertEquals(1000, users.get(3).getHierarchy());
    }

    @Test
    @DisplayName("Shift hierarchy of all users between target and over 1000")
    void shiftInactiveUsersHierarchy() {
        UserEntity user1 = createUser(Rank.CAPITAO,  "AAAA", (short) 1, "Bruce Banner", "Hulk",
                true, false, true);
        UserEntity user2 = createUser(Rank.TERCEIRO_SGT,  "BBBB", (short) 1001, "John Stewart", "Lanterna Verde",
                true, false, true);
        UserEntity user3 = createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 1002, "Barry Allen", "Flash",
                true, false, true);
        UserEntity user4 = createUser(Rank.SEGUNDO_SGT,  "DDDD", (short) 1003, "Chapolin Colorado", "Chapolin Colorado",
                true, false, true);
        repository.shiftInactiveUsersHierarchy(user2.getHierarchy());
        entityManager.flush();
        entityManager.clear();

        List<UserEntity> users = repository.findActiveUsersOrderByHierarchy();
        assertEquals(1, users.get(0).getHierarchy());
        assertEquals(1002, users.get(1).getHierarchy());
        assertEquals(1003, users.get(2).getHierarchy());
        assertEquals(1004, users.get(3).getHierarchy());
    }


    @Test
    @DisplayName("Should activate an inactive user")
    void activateUser() {
        UserEntity user = createUser(Rank.CAPITAO,  "AAAA", (short) 1, "Bruce Banner", "Hulk",
                true, false, true, false);
        repository.activateUser(user.getLpna_identifier());
        entityManager.flush();
        entityManager.clear();
        boolean userStatus = repository.findUserByLPNA(user.getLpna_identifier()).isActive();
        assertTrue(userStatus);
    }

    @Test
    @DisplayName("Should deactivate an active user")
    void deactivateUser() {
        UserEntity user = createUser(Rank.CAPITAO,  "AAAA", (short) 1, "Bruce Banner", "Hulk",
            true, false, true, true);
        repository.deactivateUser(user.getLpna_identifier());
        entityManager.flush();
        entityManager.clear();
        boolean userStatus = repository.findUserByLPNA(user.getLpna_identifier()).isActive();
        assertFalse(userStatus);

    }

    @Test
    @DisplayName("Should update user hierarchy searching by LPNA identifier")
    void updateUserHierarchy() {
        UserEntity user = createUser(Rank.CAPITAO,  "AAAA", (short) 1, "Bruce Banner", "Hulk",
                true, false, true, false);
        repository.updateUserHierarchy((short)7, "AAAA");
        entityManager.flush();
        entityManager.clear();
        short userHierarchy = repository.findUserByLPNA(user.getLpna_identifier()).getHierarchy();
        assertEquals(7, userHierarchy);
    }

    @Test
    @DisplayName("Should get a list of users from same rank, ordered by hierarchy")
    void getUsersOrderedByLowestHierarchyFromRank(){
        UserEntity user1 = createUser(Rank.CAPITAO,  "AAAA", (short) 1, "Bruce Banner", "Hulk",
                true, false, true);
        UserEntity user2 = createUser(Rank.TERCEIRO_SGT,  "BBBB", (short) 2, "John Stewart", "Lanterna Verde",
                true, false, true);
        UserEntity user3 = createUser(Rank.TERCEIRO_SGT,  "CCCC", (short) 4, "Barry Allen", "Flash",
                true, false, true);
        UserEntity user4 = createUser(Rank.TERCEIRO_SGT,  "DDDD", (short) 3, "Chapolin Colorado", "Chapolin Colorado",
                true, false, true);
        entityManager.flush();
        entityManager.clear();
        List<UserEntity> userList = repository.getUsersOrderedByLowestHierarchyFromRank(Rank.TERCEIRO_SGT);
        assertEquals("Lanterna Verde", userList.get(0).getService_name());
        assertEquals("Chapolin", userList.get(1).getService_name());
        assertEquals("Flash", userList.get(2).getService_name());
    }


}