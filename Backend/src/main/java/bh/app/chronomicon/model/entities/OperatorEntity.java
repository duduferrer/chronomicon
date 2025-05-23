package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.UserShift;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(name = "tb_shift_operators")
public class OperatorEntity extends ATCOEntity {
    private boolean isInstructor = false;

    public OperatorEntity(String id, UserEntity user, Duration workload, RosterEntity roster, UserShift shift) {
        super (id, user, workload, roster, shift);
    }

    public OperatorEntity(){
        super();
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public void setInstructor(boolean instructor) {
        isInstructor = instructor;
    }
}
