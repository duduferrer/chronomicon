package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUserEntity, String> {
     SystemUserEntity findUserBySaram(String saram);

     SystemUserEntity findUserByEmailAddress(String email);
}
