package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.Sector;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_sectorization")
public class SectorizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "control_1_id")
    private OperatorEntity control_1;

    @OneToOne
    @JoinColumn(name = "assistant_1_id")
    private OperatorEntity assistant_1;

    @OneToOne
    @JoinColumn(name = "instructor_1_id")
    private OperatorEntity instructor_1;

    @OneToOne
    @JoinColumn(name = "trainee_1_id")
    private TraineeEntity trainee_1;
    private Sector  sector_1;

    @OneToOne
    @JoinColumn(name = "control_2_id")
    private OperatorEntity control_2;

    @OneToOne
    @JoinColumn(name = "assistant_2_id")
    private OperatorEntity assistant_2;

    @OneToOne
    @JoinColumn(name = "instructor_2_id")
    private OperatorEntity instructor_2;

    @OneToOne
    @JoinColumn(name = "trainee_2_id")
    private TraineeEntity trainee_2;
    private Sector  sector_2;


    @OneToOne
    @JoinColumn(name = "control_3_id")
    private OperatorEntity control_3;

    @OneToOne
    @JoinColumn(name = "assistant_3_id")
    private OperatorEntity assistant_3;

    @OneToOne
    @JoinColumn(name = "instructor_3_id")
    private OperatorEntity instructor_3;

    @OneToOne
    @JoinColumn(name = "trainee_3_id")
    private TraineeEntity trainee_3;
    private Sector  sector_3;


    @OneToOne
    @JoinColumn(name = "control_4_id")
    private OperatorEntity control_4;

    @OneToOne
    @JoinColumn(name = "assistant_4_id")
    private OperatorEntity assistant_4;

    @OneToOne
    @JoinColumn(name = "instructor_4_id")
    private OperatorEntity instructor_4;

    @OneToOne
    @JoinColumn(name = "trainee_4_id")
    private TraineeEntity trainee_4;
    private Sector  sector_4;


    @OneToOne
    @JoinColumn(name = "control_5_id")
    private OperatorEntity control_5;

    @OneToOne
    @JoinColumn(name = "assistant_5_id")
    private OperatorEntity assistant_5;

    @OneToOne
    @JoinColumn(name = "instructor_5_id")
    private OperatorEntity instructor_5;

    @OneToOne
    @JoinColumn(name = "trainee_5_id")
    private TraineeEntity trainee_5;
    private Sector  sector_5;


    @OneToOne
    @JoinColumn(name = "control_6_id")
    private OperatorEntity control_6;

    @OneToOne
    @JoinColumn(name = "assistant_6_id")
    private OperatorEntity assistant_6;

    @OneToOne
    @JoinColumn(name = "instructor_6_id")
    private OperatorEntity instructor_6;

    @OneToOne
    @JoinColumn(name = "trainee_6_id")
    private TraineeEntity trainee_6;
    private Sector  sector_6;

    @OneToMany
    @JoinColumn(name = "supervisors_id")
    private List<SupervisorEntity> supervisor;

    @OneToMany
    @JoinColumn(name = "coordinator_id")
    private List<OperatorEntity> coordinator;


}
