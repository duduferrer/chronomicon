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
    private Timestamp created_at;
    private Timestamp updated_at;
    private UserEntity created_by;
    private UserEntity last_updated_by;


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

    public ShiftEventsEntity(ServiceShiftEntity operator_shift, EventType type, String details, Timestamp start, Timestamp end, Timestamp created_at, Timestamp updated_at, UserEntity created_by, UserEntity last_updated_by) {
        this.operator_shift = operator_shift;
        this.type = type;
        this.details = details;
        this.start = start;
        this.end = end;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by = created_by;
        this.last_updated_by = last_updated_by;

        if (operator_shift != null) {
            operator_shift.addEvent(this);
        }
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public UserEntity getCreated_by() {
        return created_by;
    }

    public void setCreated_by(UserEntity created_by) {
        this.created_by = created_by;
    }

    public UserEntity getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(UserEntity last_updated_by) {
        this.last_updated_by = last_updated_by;
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
