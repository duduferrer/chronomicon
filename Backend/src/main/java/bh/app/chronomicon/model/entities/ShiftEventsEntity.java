package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.EventType;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_shift_events")
public class ShiftEventsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private ServiceShiftEntity operator_shift;

    private EventType type;
    private String details;
    private Timestamp start;
    private Timestamp end;

    public ShiftEventsEntity (){

    }

    public ShiftEventsEntity(String id, ServiceShiftEntity operator_shift, EventType type, String details, Timestamp start, Timestamp end) {
        this.id = id;
        this.operator_shift = operator_shift;
        this.type = type;
        this.details = details;
        this.start = start;
        this.end = end;
    }
    public ShiftEventsEntity(ServiceShiftEntity operator_shift, EventType type, String details, Timestamp start, Timestamp end) {
        this.operator_shift = operator_shift;
        this.type = type;
        this.details = details;
        this.start = start;
        this.end = end;

        if (operator_shift != null) {
            operator_shift.addEvent(this);
        }
    }


    @Override
    public String toString() {
        return "ID: "+id+" detalhes: "+details;
    }

    public String getId() {
        return id;
    }

    public ServiceShiftEntity getOperator_shift() {
        return operator_shift;
    }

    public void setOperator_shift(ServiceShiftEntity operator_shift) {
        this.operator_shift = operator_shift;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}
