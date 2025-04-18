package bh.app.chronomicon.repository;

import bh.app.chronomicon.model.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
}
