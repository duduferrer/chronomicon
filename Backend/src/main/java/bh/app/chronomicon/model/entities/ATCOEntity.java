package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.UserShift;
import jakarta.persistence.*;

import java.time.Duration;

@MappedSuperclass
public abstract class ATCOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lpna_identifier")
    private UserEntity user;

    private Duration workload;

    @ManyToOne
    @JoinColumn(name = "roster_id")
    private RosterEntity roster;

    private UserShift shift;


    public ATCOEntity(String id, UserEntity user, Duration workload, RosterEntity roster, UserShift shift) {
        this.id = id;
        this.user = user;
        this.workload = workload;
        this.roster = roster;
        this.shift = shift;
    }

    public ATCOEntity(){

    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Duration getWorkload() {
        return workload;
    }

    public void setWorkload(Duration workload) {
        this.workload = workload;
    }

    public UserShift getShift() {
        return shift;
    }

    public void setShift(UserShift shift) {
        this.shift = shift;
    }

    public RosterEntity getRoster() {
        return roster;
    }

    public void setRoster(RosterEntity roster) {
        this.roster = roster;
    }

    public String getId() {
        return id;
    }

}
