package bh.app.chronomicon.model.entities;


import bh.app.chronomicon.model.enums.Sector;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "tb_sectorization")
public class SectorizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="shift_id")
    private ShiftEntity shift;
    private Sector sectors;
    private int console;
    private ATCOEntity ctr;
    private ATCOEntity ass;
    private ATCOEntity ctr_trainee;
    private ATCOEntity ass_trainee;
    private Timestamp start_time;
    private Timestamp end_time;

    public SectorizationEntity(String id, ShiftEntity shift, Sector sectors, int console, ATCOEntity ctr, ATCOEntity ass, ATCOEntity ctr_trainee, ATCOEntity ass_trainee, Timestamp start_time, Timestamp end_time) {
        this.id = id;
        this.shift = shift;
        this.sectors = sectors;
        this.console = console;
        this.ctr = ctr;
        this.ass = ass;
        this.ctr_trainee = ctr_trainee;
        this.ass_trainee = ass_trainee;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public SectorizationEntity() {
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public ATCOEntity getAss_trainee() {
        return ass_trainee;
    }

    public void setAss_trainee(ATCOEntity ass_trainee) {
        this.ass_trainee = ass_trainee;
    }

    public ATCOEntity getCtr_trainee() {
        return ctr_trainee;
    }

    public void setCtr_trainee(ATCOEntity ctr_trainee) {
        this.ctr_trainee = ctr_trainee;
    }

    public ATCOEntity getAss() {
        return ass;
    }

    public void setAss(ATCOEntity ass) {
        this.ass = ass;
    }

    public ATCOEntity getCtr() {
        return ctr;
    }

    public void setCtr(ATCOEntity ctr) {
        this.ctr = ctr;
    }

    public int getConsole() {
        return console;
    }

    public void setConsole(int console) {
        this.console = console;
    }
    public Sector getSectors() {
        return sectors;
    }

    public void setSectors(Sector sectors) {
        this.sectors = sectors;
    }

    public ShiftEntity getShift() {
        return shift;
    }

    public void setShift(ShiftEntity shift) {
        this.shift = shift;
    }

    public String getId() {
        return id;
    }
    public Duration getDuration(){
        return Duration.between (start_time.toLocalDateTime (), end_time.toLocalDateTime ());
    }

    @Override
    public String toString() {
        if(console==0){
            return "Coordenador: " + ctr +
                    ", Inicio: " + start_time.toLocalDateTime ()+
                    ", Fim: " + end_time.toLocalDateTime ();
        } else if (ctr_trainee != null) {
            return  "Setores: " + sectors +
                    ", Console " + console +
                    ", Controle: " + ctr +
                    ", Assistente: " + ass +
                    ", Estagiario CTR" + ctr_trainee +
                    ", Inicio: " + start_time.toLocalDateTime ()+
                    ", Fim: " + end_time.toLocalDateTime ();
        } else if (ass_trainee != null) {
            return "Setores: " + sectors +
                    ", Console " + console +
                    ", Controle: " + ctr +
                    ", Assistente: " + ass +
                    ", Estagiario ASS" + ass_trainee +
                    ", Inicio: " + start_time.toLocalDateTime () +
                    ", Fim: " + end_time.toLocalDateTime ();
        }
        else{
            return  "Setores: " + sectors +
                    ", Console " + console +
                    ", Controle: " + ctr +
                    ", Assistente: " + ass +
                    ", Inicio: " + start_time.toLocalDateTime ()+
                    ", Fim: " + end_time.toLocalDateTime ();
        }
    }
}
