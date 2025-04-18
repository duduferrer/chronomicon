package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.Shift;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="tb_roster")
public class RosterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    @JoinColumn(name = "supervisors_id")
    private List<SupervisorEntity> supervisors;

    @OneToMany
    @JoinColumn(name = "operators_id")
    private List<OperatorEntity> operators;

    @OneToMany
    @JoinColumn(name = "trainees_id")
    private List<TraineeEntity> trainees;

    private int sectorization;
    private LocalDate date;
    private Shift shift;


}
