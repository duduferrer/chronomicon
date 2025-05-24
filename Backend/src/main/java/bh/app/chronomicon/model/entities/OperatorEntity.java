package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.Shift;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
public class OperatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lpna_identifier")
    private UserEntity user;

    private Duration workload;

    private Shift operator_shift;
    private boolean isSupervisor;
    private boolean isTrainee;
    private boolean isInstructor;
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private ShiftEntity shift;


    public OperatorEntity(String id, UserEntity user, Duration workload, Shift shift,
                          boolean isSupervisor, boolean isTrainee, boolean isInstructor) {
        this.id = id;
        this.user = user;
        this.workload = workload;
        this.operator_shift = shift;
        this.isSupervisor = isSupervisor;
        this.isTrainee = isTrainee;
        this.isInstructor = isInstructor;
    }

    public OperatorEntity(String id, UserEntity user, Duration workload, Shift shift) {
        this.id = id;
        this.user = user;
        this.workload = workload;
        this.operator_shift = shift;
    }

    public OperatorEntity(){

    }

    public OperatorEntity(UserEntity user, Duration workload, Shift shift, boolean isSupervisor,
                          boolean isTrainee, boolean isInstructor) {
        this.user = user;
        this.workload = workload;
        this.operator_shift = shift;
        this.isSupervisor = isSupervisor;
        this.isTrainee = isTrainee;
        this.isInstructor = isInstructor;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Duration getWorkload() {
        return workload;
    }

    public void setWorkload(Duration workload) {
        this.workload = workload;
    }

    public Shift getOperator_shift() {
        return operator_shift;
    }

    public void setOperator_shift(Shift operator_shift) {
        this.operator_shift = operator_shift;
    }

    public String getId() {
        return id;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }

    public boolean isTrainee() {
        return isTrainee;
    }

    public void setTrainee(boolean trainee) {
        isTrainee = trainee;
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public void setInstructor(boolean instructor) {
        isInstructor = instructor;
    }
}
