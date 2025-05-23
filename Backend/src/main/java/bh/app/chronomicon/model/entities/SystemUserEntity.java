package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.Role;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_sys_users")
public class SystemUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Role role;

    @OneToOne
    @JoinColumn(name = "user_lpna", referencedColumnName = "lpna_identifier")
    private UserEntity user;
    private Timestamp created_at;
    private Timestamp updated_at;
    private boolean isActive = true;
    private Timestamp last_login;
    private String password;
    private boolean isCaster=false;
    private boolean isPoolCaster=false;
    private boolean isHiker=false;

    public SystemUserEntity(){

    }
    public SystemUserEntity(Role role, UserEntity user, Timestamp created_at, Timestamp updated_at, boolean isActive, Timestamp last_login, String password, boolean isCaster, boolean isPoolCaster, boolean isHiker) {
        this.role = role;
        this.user = user;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isActive = isActive;
        this.last_login = last_login;
        this.password = password;
        this.isCaster = isCaster;
        this.isPoolCaster = isPoolCaster;
        this.isHiker = isHiker;
    }

    public SystemUserEntity(String id, Role role, UserEntity user, Timestamp created_at, Timestamp updated_at, boolean isActive, Timestamp last_login, String password, boolean isCaster, boolean isPoolCaster, boolean isHiker) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isActive = isActive;
        this.last_login = last_login;
        this.password = password;
        this.isCaster = isCaster;
        this.isPoolCaster = isPoolCaster;
        this.isHiker = isHiker;
    }

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCaster() {
        return isCaster;
    }

    public void setCaster(boolean caster) {
        isCaster = caster;
    }

    public boolean isPoolCaster() {
        return isPoolCaster;
    }

    public void setPoolCaster(boolean poolCaster) {
        isPoolCaster = poolCaster;
    }

    public boolean isHiker() {
        return isHiker;
    }

    public void setHiker(boolean hiker) {
        isHiker = hiker;
    }
}
