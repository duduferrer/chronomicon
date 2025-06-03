package bh.app.chronomicon.repository;


import bh.app.chronomicon.model.entities.OperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorEntity, String> {

    @Query("SELECT operator from OperatorEntity operator " +
            "WHERE operator.service_shift.id = :id " +
            "AND operator.isTrainee = true " +
            "ORDER BY operator.user.hierarchy ASC")
    List<OperatorEntity> findTraineesOrderByHierarchy(@Param("id") int id);

    @Query("SELECT operator from OperatorEntity operator " +
            "WHERE operator.service_shift.id = :id " +
            "AND operator.isSupervisor = true " +
            "ORDER BY operator.user.hierarchy ASC")
    List<OperatorEntity> findSupsOrderByHierarchy(@Param("id") int id);

    @Query("SELECT operator from OperatorEntity operator " +
            "WHERE operator.service_shift.id = :id " +
            "AND operator.isInstructor = true " +
            "ORDER BY operator.user.hierarchy ASC")
    List<OperatorEntity> findInstsOrderByHierarchy(@Param("id") int id);

    @Query("SELECT operator from OperatorEntity operator " +
            "WHERE operator.service_shift.id = :id " +
            "AND operator.isTrainee = false " +
            "AND operator.isInstructor = false " +
            "AND operator.isSupervisor = false " +
            "ORDER BY operator.user.hierarchy ASC")
    List<OperatorEntity> findOperatorsOnlyOrderByHierarchy(@Param("id") int id);

    @Query("SELECT operator from OperatorEntity operator " +
            "WHERE operator.service_shift.id = :id " +
            "ORDER BY operator.user.hierarchy ASC")
    List<OperatorEntity> findAllStaffOrderByHierarchy(@Param("id") int id);

}
