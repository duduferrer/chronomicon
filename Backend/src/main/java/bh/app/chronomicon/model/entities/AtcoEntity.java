package bh.app.chronomicon.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_staff")
public class AtcoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String lpna_identifier;
    @Column(unique=true)
    private short hierarchy;
    private boolean supervisor;
    private boolean instructor;
    private boolean trainee;
    private boolean isActive = true;
    private Timestamp created_at;
    private Timestamp updated_at;
    @OneToOne
    @JoinColumn(name = "core_user_info_id", referencedColumnName = "id")
    private CoreUserInformationEntity coreUserInformationEntity;
    @OneToOne
    @JoinColumn(name = "id")
    private SystemUserEntity systemUserEntity;


    public AtcoEntity() {
    }
    
    public AtcoEntity(long id, String lpna_identifier, short hierarchy, boolean supervisor, boolean instructor, boolean trainee, boolean isActive, Timestamp created_at, Timestamp updated_at, CoreUserInformationEntity coreUserInformationEntity, SystemUserEntity systemUserEntity) {
        this.id = id;
        this.lpna_identifier = lpna_identifier;
        this.hierarchy = hierarchy;
        this.supervisor = supervisor;
        this.instructor = instructor;
        this.trainee = trainee;
        this.isActive = isActive;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.coreUserInformationEntity = coreUserInformationEntity;
        this.systemUserEntity = systemUserEntity;
    }
    
    public AtcoEntity(String lpna_identifier, short hierarchy, boolean supervisor, boolean instructor, boolean trainee, boolean isActive, Timestamp created_at, Timestamp updated_at, CoreUserInformationEntity coreUserInformationEntity, SystemUserEntity systemUserEntity) {
        this.lpna_identifier = lpna_identifier;
        this.hierarchy = hierarchy;
        this.supervisor = supervisor;
        this.instructor = instructor;
        this.trainee = trainee;
        this.isActive = isActive;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.coreUserInformationEntity = coreUserInformationEntity;
        this.systemUserEntity = systemUserEntity;
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
    
    public CoreUserInformationEntity getCoreUserInformationEntity() {
        return coreUserInformationEntity;
    }
    
    public void setCoreUserInformationEntity(CoreUserInformationEntity coreUserInformationEntity) {
        this.coreUserInformationEntity = coreUserInformationEntity;
    }
    
    public SystemUserEntity getSystemUserEntity() {
        return systemUserEntity;
    }
    
    public void setSystemUserEntity(SystemUserEntity systemUserEntity) {
        this.systemUserEntity = systemUserEntity;
    }
}
