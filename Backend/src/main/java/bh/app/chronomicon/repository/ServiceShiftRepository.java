package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.ServiceShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceShiftRepository extends JpaRepository<ServiceShiftEntity, Integer> {

}
