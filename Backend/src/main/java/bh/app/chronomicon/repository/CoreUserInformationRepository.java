package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.CoreUserInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoreUserInformationRepository extends JpaRepository<CoreUserInformationEntity, String> {
	
	Optional<CoreUserInformationEntity> findBySaram(String saram);
	
}
