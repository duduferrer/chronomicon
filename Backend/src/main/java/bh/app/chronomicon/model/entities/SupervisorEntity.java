package bh.app.chronomicon.model.entities;

import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(name = "tb_shift_supervisors")
public class SupervisorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lpna_identifier")
    private StaffEntity operator;

    private Duration workload;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private RosterEntity shift;

    public SupervisorEntity() {
    }

    public SupervisorEntity(int id, StaffEntity operator, Duration workload, RosterEntity shift) {
        this.id = id;
        this.operator = operator;
        this.workload = workload;
        this.shift = shift;
    }

    public int getId() {
        return id;
    }


    public StaffEntity getOperator() {
        return operator;
    }

    public void setOperator(StaffEntity operator) {
        this.operator = operator;
    }

    public Duration getWorkload() {
        return workload;
    }

    public void setWorkload(Duration workload) {
        this.workload = workload;
    }

    public RosterEntity getShift() {
        return shift;
    }

    public void setShift(RosterEntity shift) {
        this.shift = shift;
    }
}
