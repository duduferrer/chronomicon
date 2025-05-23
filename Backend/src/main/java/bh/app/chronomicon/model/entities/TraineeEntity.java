package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.UserShift;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Table(name = "tb_shift_trainees")
public class TraineeEntity extends ATCOEntity{

    public TraineeEntity(String id, UserEntity user, Duration workload, RosterEntity roster, UserShift shift) {
        super (id, user, workload, roster, shift);
    }

    public TraineeEntity(){
        super();
    }
}
