package bh.app.chronomicon.model.enums;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public enum ShiftType {
    M("Manhã", LocalTime.of (6,15), LocalTime.of (13,30)),
    RM1("Ref Manhã 1", LocalTime.of (7,30), LocalTime.of (13,0)),
    RM2("Ref Manhã 2", LocalTime.of (8,0), LocalTime.of (13,0)),
    RM3("Ref Manhã 3", LocalTime.of (8,30), LocalTime.of (15,30)),
    T("Tarde", LocalTime.of (13,15), LocalTime.of (20,45)),
    RT1("Ref Tarde 1", LocalTime.of (12,0),LocalTime.of (19,0)),
    RT2("Ref Tarde 2", LocalTime.of (17,30),LocalTime.of (22,30)),
    RT3("Ref Tarde 3", LocalTime.of (15,30), LocalTime.of (22,30)),
    P("Pernoite", LocalTime.of (20,30), LocalTime.of (6,30)),
    SGD("Sargento de Dia", LocalTime.of (8,0), LocalTime.of (8,0)),
    EX("Expediente", LocalTime.of (8,0), LocalTime.of (16,0)),
    mEX("Meio Expediente", LocalTime.of (8,0), LocalTime.of (12,0)),
    SM("Supervisor Manhã", LocalTime.of (6,15), LocalTime.of (13,30)),
    ST("Supervisor Tarde", LocalTime.of (13,15), LocalTime.of (20,45)),
    TS("Tarde Sobreaviso", LocalTime.of (13,15), LocalTime.of (20,45)),
    MS("Manhã Sobreaviso", LocalTime.of (6,15), LocalTime.of (13,30)),
    PS("Pernoite Sobreaviso", LocalTime.of (20,30), LocalTime.of (6,30));
    private final String caption;
    private final LocalTime startTime;
    private final LocalTime endTime;

    ShiftType(String caption, LocalTime startTime, LocalTime endTime){
        this.caption = caption;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCaption(){
        return caption;
    }

    public String getStartTime(){
        return startTime.format (DateTimeFormatter.ofPattern ("HH:mm"));
    }

    public String getEndTime(){
        return endTime.format (DateTimeFormatter.ofPattern ("HH:mm"));
    }

    public Duration getDuration(){
        Duration shiftDuration;
        if(endTime.isBefore (startTime) || startTime.equals (endTime)){
            shiftDuration = Duration.between (startTime, endTime.plusHours (24));
        }else{
            shiftDuration = Duration.between (startTime, endTime);
        }
        if(caption.contains ("Sobreaviso")){
            shiftDuration = shiftDuration.dividedBy (3);
        }
        return shiftDuration;
    }
}
