package bh.app.chronomicon.repository;


import bh.app.chronomicon.model.entities.OperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorEntity, String> {

}
