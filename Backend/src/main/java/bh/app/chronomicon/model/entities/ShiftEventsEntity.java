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
    private Timestamp eventStart;
    private Timestamp eventEnd;
    private Timestamp created_at;
    private Timestamp updated_at;
    @ManyToOne
    @JoinColumn(name="creator_lpna", referencedColumnName = "lpna_identifier")
    private AtcoEntity created_by;
    @ManyToOne
    @JoinColumn(name="updater_lpna", referencedColumnName = "lpna_identifier")
    private AtcoEntity last_updated_by;


    public ShiftEventsEntity (){

    }

    public ShiftEventsEntity(String id, ServiceShiftEntity operator_shift, EventType type, String details, Timestamp eventStart, Timestamp eventEnd) {
        this.id = id;
        this.operator_shift = operator_shift;
        this.type = type;
        this.details = details;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }
    public ShiftEventsEntity(ServiceShiftEntity operator_shift, EventType type, String details, Timestamp eventStart, Timestamp eventEnd) {
        this.operator_shift = operator_shift;
        this.type = type;
        this.details = details;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;

        if (operator_shift != null) {
            operator_shift.addEvent(this);
        }
    }

    public ShiftEventsEntity(ServiceShiftEntity operator_shift, EventType type, String details, Timestamp eventStart, Timestamp eventEnd, Timestamp created_at, Timestamp updated_at, AtcoEntity created_by, AtcoEntity last_updated_by) {
        this.operator_shift = operator_shift;
        this.type = type;
        this.details = details;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
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

    public AtcoEntity getCreated_by() {
        return created_by;
    }

    public void setCreated_by(AtcoEntity created_by) {
        this.created_by = created_by;
    }

    public AtcoEntity getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(AtcoEntity last_updated_by) {
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

    public Timestamp getEventStart() {
        return eventStart;
    }

    public void setEventStart(Timestamp eventStart) {
        this.eventStart = eventStart;
    }

    public Timestamp getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Timestamp eventEnd) {
        this.eventEnd = eventEnd;
    }
}
