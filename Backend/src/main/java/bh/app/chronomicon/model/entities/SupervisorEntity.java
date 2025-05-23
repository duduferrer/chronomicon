package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.UserShift;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(name = "tb_shift_supervisors")
public class SupervisorEntity extends ATCOEntity{

    private boolean isInstructor = false;


    public SupervisorEntity(String id, UserEntity user, Duration workload, RosterEntity roster, UserShift shift) {
        super (id, user, workload, roster, shift);
    }

    public SupervisorEntity(){
        super();
    }

    public boolean isInstructor() {
        return isInstructor;
    }

    public void setInstructor(boolean instructor) {
        isInstructor = instructor;
    }
}
