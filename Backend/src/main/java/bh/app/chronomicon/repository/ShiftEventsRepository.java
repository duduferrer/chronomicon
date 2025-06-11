package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.ShiftEventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftEventsRepository extends JpaRepository<ShiftEventsEntity,String> {
}
