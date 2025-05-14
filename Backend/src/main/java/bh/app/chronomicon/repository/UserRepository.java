package bh.app.chronomicon.repository;
import bh.app.chronomicon.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.lpna_identifier = :lpna_identifier")
    boolean existsByLpnaIdentifier(@Param("lpna_identifier") String lpna_identifier);

    @Query("SELECT user from UserEntity user WHERE user.activeUser = true AND user.supervisor = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findSupsOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.activeUser = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findActiveUsersOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.activeUser = true AND user.instructor = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findInstsOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.activeUser = true AND user.trainee = true ORDER BY user.hierarchy ASC")
    List<UserEntity> findTraineesOrderByHierarchy();

    @Query("SELECT user from UserEntity user WHERE user.activeUser = true AND user.trainee = false AND user.instructor = false AND user.supervisor = false ORDER BY user.hierarchy ASC")
    List<UserEntity> findOnlyOpsOrderByHierarchy();

    @Query("SELECT user FROM UserEntity user WHERE user.id = :id")
    UserEntity findUserById(@Param("id") Long id);

    @Query("SELECT user FROM UserEntity user WHERE user.lpna_identifier = :lpna_identifier")
    UserEntity findUserByLPNA(@Param("lpna_identifier") String lpna_identifier);

}
