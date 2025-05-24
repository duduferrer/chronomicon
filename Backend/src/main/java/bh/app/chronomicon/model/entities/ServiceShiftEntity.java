package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.ShiftType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_shift")
public class ServiceShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private ShiftType shift;
    @OneToMany(mappedBy = "operator_shift")
    private SectorizationEntity sectorization;
    @OneToMany(mappedBy = "operator_shift")
    private ShiftEventsEntity events;
    @OneToMany(mappedBy = "shift")
    private List<OperatorEntity> operators;
    private boolean isActive = true;

    public ServiceShiftEntity(int id, LocalDate date, ShiftType shift, SectorizationEntity sectorization, ShiftEventsEntity events, List<OperatorEntity> operators) {
        this.id = id;
        this.date = date;
        this.shift = shift;
        this.sectorization = sectorization;
        this.events = events;
        this.operators = operators;
    }

    public ServiceShiftEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftType getShift() {
        return shift;
    }

    public void setShift(ShiftType shift) {
        this.shift = shift;
    }

    public SectorizationEntity getSectorization() {
        return sectorization;
    }

    public void setSectorization(SectorizationEntity sectorization) {
        this.sectorization = sectorization;
    }

    public ShiftEventsEntity getEvents() {
        return events;
    }

    public void setEvents(ShiftEventsEntity events) {
        this.events = events;
    }

    public List<OperatorEntity> getOperators() {
        return operators;
    }

    public void setOperators(List<OperatorEntity> operators) {
        this.operators = operators;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
