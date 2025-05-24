package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<ShiftEntity, Integer> {

}
