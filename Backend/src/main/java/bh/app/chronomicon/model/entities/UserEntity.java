package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.model.enums.Rank;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_staff")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String lpna_identifier;
    @Column(unique=true)
    private short hierarchy;
    private String full_name;
    @Enumerated(EnumType.STRING)
    private Rank rank;
    private String service_name;
    private boolean supervisor;
    private boolean instructor;
    private boolean trainee;
    private boolean isActive = true;


    public UserEntity() {
    }

    public UserEntity(UserDTO userDTO){
        this.rank = userDTO.rank();
        this.lpna_identifier = userDTO.lpna_identifier();
        this.hierarchy = userDTO.hierarchy();
        this.full_name = userDTO.full_name();
        this.service_name = userDTO.service_name();
        this.supervisor = userDTO.supervisor();
        this.instructor = userDTO.instructor();
        this.trainee = userDTO.trainee();

    }

    public UserEntity(Rank rank, long id, String lpna_identifier, short hierarchy, String full_name, String service_name,
                      boolean supervisor, boolean instructor, boolean trainee) {
        this.rank = rank;
        this.id = id;
        this.lpna_identifier = lpna_identifier;
        this.hierarchy = hierarchy;
        this.full_name = full_name;
        this.service_name = service_name;
        this.supervisor = supervisor;
        this.instructor = instructor;
        this.trainee = trainee;
        this.isActive = true;
    }
    public UserEntity(Rank rank, String lpna_identifier, short hierarchy, String full_name, String service_name,
                      boolean supervisor, boolean instructor, boolean trainee, Boolean isActive) {
        this.rank = rank;
        this.lpna_identifier = lpna_identifier;
        this.hierarchy = hierarchy;
        this.full_name = full_name;
        this.service_name = service_name;
        this.supervisor = supervisor;
        this.instructor = instructor;
        this.trainee = trainee;
        this.isActive = isActive != null ? isActive : true;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public long getId() {
        return id;
    }

    public String getLpna_identifier() {
        return lpna_identifier;
    }

    public void setLpna_identifier(String lpna_identifier) {
        this.lpna_identifier = lpna_identifier;
    }

    public short getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(short hierarchy) {
        this.hierarchy = hierarchy;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }

    public boolean isInstructor() {
        return instructor;
    }

    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

    public boolean isTrainee() {
        return trainee;
    }

    public void setTrainee(boolean trainee) {
        this.trainee = trainee;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
