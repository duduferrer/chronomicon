package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.Shift;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="tb_roster")
public class RosterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "roster")
    private List<SupervisorEntity> supervisors;

    @OneToMany(mappedBy = "roster")
    private List<OperatorEntity> operators;

    @OneToMany(mappedBy = "roster")
    private List<TraineeEntity> trainees;


}
