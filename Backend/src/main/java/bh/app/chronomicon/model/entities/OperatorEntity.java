package bh.app.chronomicon.model.entities;


import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(name = "tb_shift_operators")
public class OperatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "lpna_identifier")
    private UserEntity operator;

    private Duration workload;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private RosterEntity shift;

    private boolean rm1 = false;
    private boolean rm2 = false;
    private boolean rm3 = false;
    private boolean rt1 = false;
    private boolean rt2 = false;
    private boolean rt3 = false;

    public OperatorEntity() {
    }

    public OperatorEntity(int id, UserEntity operator, Duration workload, RosterEntity shift, boolean rm1, boolean rm2, boolean rm3, boolean rt1, boolean rt2, boolean rt3) {
        this.id = id;
        this.operator = operator;
        this.workload = workload;
        this.shift = shift;
        this.rm1 = rm1;
        this.rm2 = rm2;
        this.rm3 = rm3;
        this.rt1 = rt1;
        this.rt2 = rt2;
        this.rt3 = rt3;
    }

    public UserEntity getOperator() {
        return operator;
    }

    public void setOperator(UserEntity operator) {
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

    public boolean isRm1() {
        return rm1;
    }

    public void setRm1(boolean rm1) {
        this.rm1 = rm1;
    }

    public boolean isRm2() {
        return rm2;
    }

    public void setRm2(boolean rm2) {
        this.rm2 = rm2;
    }

    public boolean isRm3() {
        return rm3;
    }

    public void setRm3(boolean rm3) {
        this.rm3 = rm3;
    }

    public boolean isRt1() {
        return rt1;
    }

    public void setRt1(boolean rt1) {
        this.rt1 = rt1;
    }

    public boolean isRt2() {
        return rt2;
    }

    public void setRt2(boolean rt2) {
        this.rt2 = rt2;
    }

    public boolean isRt3() {
        return rt3;
    }

    public void setRt3(boolean rt3) {
        this.rt3 = rt3;
    }

    public int getId() {
        return id;
    }
}
