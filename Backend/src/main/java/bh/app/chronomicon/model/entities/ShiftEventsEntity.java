package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.EventType;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_shift_events")
public class ShiftEventsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
            @JoinColumn(name = "shift_id")
    ShiftEntity shift;

    EventType type;
    String details;

}
