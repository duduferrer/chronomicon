package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.ForgottenPasswordEntity;
import bh.app.chronomicon.model.entities.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgottenPasswordRepository extends JpaRepository<ForgottenPasswordEntity, String> {
    Optional<ForgottenPasswordEntity> findByPasswordRefreshToken(String passwordRefreshToken);
    ForgottenPasswordEntity findBySystemUserEntity(SystemUserEntity systemUserEntity);
}
