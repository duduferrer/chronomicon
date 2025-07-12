package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUserEntity, String> {
     
     @Query("SELECT user FROM SystemUserEntity user WHERE user.coreUserInformationEntity.saram = :saram")
     SystemUserEntity findUserBySaram(@Param("saram") String saram);
     
     @Query("SELECT user FROM SystemUserEntity user WHERE user.coreUserInformationEntity.emailAddress = :email")
     SystemUserEntity findUserByEmailAddress(@Param("email")String email);
}
