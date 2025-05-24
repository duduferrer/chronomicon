package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.exception.NotFoundException;
import bh.app.chronomicon.exception.ServerException;
import bh.app.chronomicon.model.enums.ShiftType;
import bh.app.chronomicon.service.OperatorService;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_shift")
public class ServiceShiftEntity {
    private static final Logger log = LoggerFactory.getLogger(ServiceShiftEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private ShiftType shift;
    @OneToMany(mappedBy = "operator_shift")
    private SectorizationEntity sectorization;
    @OneToMany(mappedBy = "operator_shift")
    private List<ShiftEventsEntity> events;
    @OneToMany(mappedBy = "shift")
    private List<OperatorEntity> operators;
    private boolean isActive = true;
    private Timestamp opening_time;
    private Timestamp closing_time;
    private UserEntity closed_by;
    private Timestamp update_after_closing;

    public ServiceShiftEntity(int id, LocalDate date, ShiftType shift, SectorizationEntity sectorization, List<ShiftEventsEntity> events, List<OperatorEntity> operators) {
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

    public List<ShiftEventsEntity> getEvents() {
        return events;
    }

    public void setEvents(List<ShiftEventsEntity> events) {
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

    public void addEvent(ShiftEventsEntity event){
        this.events.add (event);
        log.info ("Evento {} adicionado", event);
    }
    public void removeEvent(ShiftEventsEntity event){
        try{
            boolean removed = this.events.remove (event);
            log.info ("Evento {} removido", event);
            if(!removed){
                log.warn ("Evento {} não encontrado.", event);
                throw new NotFoundException ("Evento "+event+" não encontrado.");
            }
        } catch (RuntimeException e) {
            log.error ("Não foi possivel remover evento: {}", event);
            throw new ServerException ("Não foi possivel remover evento.");
        }

    }

    public Timestamp getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(Timestamp opening_time) {
        this.opening_time = opening_time;
    }

    public Timestamp getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(Timestamp closing_time) {
        this.closing_time = closing_time;
    }

    public UserEntity getClosed_by() {
        return closed_by;
    }

    public void setClosed_by(UserEntity closed_by) {
        this.closed_by = closed_by;
    }

    public Timestamp getUpdate_after_closing() {
        return update_after_closing;
    }

    public void setUpdate_after_closing(Timestamp update_after_closing) {
        this.update_after_closing = update_after_closing;
    }
}
