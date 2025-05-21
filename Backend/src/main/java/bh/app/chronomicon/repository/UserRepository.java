package bh.app.chronomicon.repository;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.lpna_identifier = :lpna_identifier")
    boolean existsByLpnaIdentifier(@Param("lpna_identifier") String lpna_identifier);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.hierarchy = :hierarchy")
    boolean existsByHierarchy(@Param("hierarchy") short hierarchy);

    @Modifying
    @Query("UPDATE UserEntity user SET user.hierarchy = user.hierarchy + 1 WHERE user.hierarchy >= :hierarchy AND user.hierarchy < 1000")
    void shiftActiveUsersHierarchy(@Param("hierarchy") short hierarchy);

    @Modifying
    @Query("UPDATE UserEntity user SET user.hierarchy = user.hierarchy + 1 WHERE user.hierarchy >= :hierarchy AND user.hierarchy > 1000")
    void shiftInactiveUsersHierarchy(@Param("hierarchy") short hierarchy);

    @Modifying
    @Query("UPDATE UserEntity user SET user.isActive = true WHERE user.lpna_identifier = :lpna_identifier")
    void activateUser(@Param("lpna_identifier") String lpna_identifier);

    @Modifying
    @Query("UPDATE UserEntity user SET user.isActive = false WHERE user.lpna_identifier = :lpna_identifier")
    void deactivateUser(@Param("lpna_identifier") String lpna_identifier);

    @Modifying
    @Query("UPDATE UserEntity user SET user.hierarchy = :hierarchy WHERE user.lpna_identifier = :lpna_identifier")
    void updateUserHierarchy(@Param("hierarchy") short hierarchy, @Param("lpna_identifier") String lpna_identifier);

    @Query("SELECT user from UserEntity user WHERE user.rank = :rank AND user.isActive = true ORDER BY user.hierarchy DESC")
    List<UserEntity> getUsersOrderedByLowestHierarchyFromRank(@Param("rank") Rank rank);

    @Query("SELECT user from UserEntity user WHERE user.isActive = true AND user.supervisor = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findSupsOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.isActive = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findActiveUsersOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.isActive = true AND user.instructor = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findInstsOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.isActive = true AND user.trainee = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findTraineesOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.isActive = true AND user.trainee = false AND user.instructor = false AND user.supervisor = false ORDER BY user.hierarchy ASC")
    List<UserEntity> findOnlyOpsOrderByHierarchy();

    @Query("SELECT user FROM UserEntity user WHERE user.id = :id")
    UserEntity findUserById(@Param("id") Long id);

    @Query("SELECT user FROM UserEntity user WHERE user.lpna_identifier = :lpna_identifier")
    UserEntity findUserByLPNA(@Param("lpna_identifier") String lpna_identifier);

}
