package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.Shift;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_shift")
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "roster_id")
    private RosterEntity roster;

    private LocalDate date;
    private Shift shift;
    @OneToMany(mappedBy = "shift")
    private SectorizationEntity sectorization;
    @OneToMany(mappedBy = "shift")
    @JoinColumn(name = "events_id")
    private ShiftEventsEntity events;


}
