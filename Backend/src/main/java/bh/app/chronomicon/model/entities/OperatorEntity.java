package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.model.enums.WorkloadOperation;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
public class OperatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="lpna_identifier", referencedColumnName = "lpna_identifier")
    private AtcoEntity user;

    private Duration workload;

    private ShiftType shift_type;
    private boolean isSupervisor;
    private boolean isTrainee;
    private boolean isInstructor;
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private ServiceShiftEntity service_shift;


    public OperatorEntity(String id, AtcoEntity user, Duration workload, ShiftType serviceShift,
						  boolean isSupervisor, boolean isTrainee, boolean isInstructor) {
        this.id = id;
        this.user = user;
        this.workload = workload;
        this.shift_type = serviceShift;
        this.isSupervisor = isSupervisor;
        this.isTrainee = isTrainee;
        this.isInstructor = isInstructor;
    }

    public OperatorEntity(String id, AtcoEntity user, Duration workload, ShiftType serviceShift) {
        this.id = id;
        this.user = user;
        this.workload = workload;
        this.shift_type = serviceShift;
    }

    public OperatorEntity(){

    }

    public OperatorEntity(AtcoEntity user, Duration workload, ShiftType shift, boolean isSupervisor,
						  boolean isTrainee, boolean isInstructor, ServiceShiftEntity serviceShift) {
        this.user = user;
        this.workload = workload;
        this.shift_type = shift;
        this.isSupervisor = isSupervisor;
        this.isTrainee = isTrainee;
        this.isInstructor = isInstructor;
        this.service_shift = serviceShift;
    }

    public AtcoEntity getUser() {
        return user;
    }

    public void setUser(AtcoEntity user) {
        this.user = user;
    }

    public Duration getWorkload() {
        return workload;
    }

    public void setWorkload(Duration delta, WorkloadOperation op) {
        this.workload = op.apply (this.workload, delta);
    }

    public ShiftType getShift_type() {
        return shift_type;
    }

    public void setShift_type(ShiftType shift_type) {
        this.shift_type = shift_type;
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

    public ServiceShiftEntity getService_shift() {
        return service_shift;
    }

    public void setService_shift(ServiceShiftEntity service_shift) {
        this.service_shift = service_shift;
    }
}
