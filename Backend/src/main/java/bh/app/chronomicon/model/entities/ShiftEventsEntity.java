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
    ServiceShiftEntity operator_shift;

    EventType type;
    String details;

    @Override
    public String toString() {
        return "ID: "+id+" detalhes: "+details;
    }
}
