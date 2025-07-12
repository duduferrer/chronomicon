package bh.app.chronomicon.model.entities;

import bh.app.chronomicon.model.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_sys_users")
public class SystemUserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Role role;

    @OneToOne
    @JoinColumn(name = "id")
    private AtcoEntity atco;
    @OneToOne
    @JoinColumn(name = "id")
    private CoreUserInformationEntity coreUserInformationEntity;
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
    
    public SystemUserEntity(String id, Role role, AtcoEntity atco, CoreUserInformationEntity coreUserInformationEntity, Timestamp created_at, Timestamp updated_at, boolean isActive, Timestamp last_login, String password, boolean isCaster, boolean isPoolCaster, boolean isHiker) {
        this.id = id;
        this.role = role;
        this.atco = atco;
        this.coreUserInformationEntity = coreUserInformationEntity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isActive = isActive;
        this.last_login = last_login;
        this.password = password;
        this.isCaster = isCaster;
        this.isPoolCaster = isPoolCaster;
        this.isHiker = isHiker;
    }
    
    public SystemUserEntity(Role role, AtcoEntity atco, CoreUserInformationEntity coreUserInformationEntity, Timestamp created_at, Timestamp updated_at, boolean isActive, Timestamp last_login, String password, boolean isCaster, boolean isPoolCaster, boolean isHiker) {
        this.role = role;
        this.atco = atco;
        this.coreUserInformationEntity = coreUserInformationEntity;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isActive = isActive;
        this.last_login = last_login;
        this.password = password;
        this.isCaster = isCaster;
        this.isPoolCaster = isPoolCaster;
        this.isHiker = isHiker;
    }
    
    public SystemUserEntity(Role role, AtcoEntity atco, Timestamp created_at, Timestamp updated_at, boolean isActive, Timestamp last_login, String password, boolean isCaster, boolean isPoolCaster, boolean isHiker) {
        this.role = role;
        this.atco = atco;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isActive = isActive;
        this.last_login = last_login;
        this.password = password;
        this.isCaster = isCaster;
        this.isPoolCaster = isPoolCaster;
        this.isHiker = isHiker;
    }

    public SystemUserEntity(String id, Role role, AtcoEntity atco, Timestamp created_at, Timestamp updated_at, boolean isActive, Timestamp last_login, String password, boolean isCaster, boolean isPoolCaster, boolean isHiker) {
        this.id = id;
        this.role = role;
        this.atco = atco;
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

    public AtcoEntity getAtco() {
        return atco;
    }

    public void setAtco(AtcoEntity atco) {
        this.atco = atco;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of (new SimpleGrantedAuthority ("ROLE_"+getRole ()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return coreUserInformationEntity.getSaram();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive ();
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
    
    public CoreUserInformationEntity getCoreUserInformation() {
        return coreUserInformationEntity;
    }
    
    public void setCoreUserInformation(CoreUserInformationEntity coreUserInformationEntity) {
        this.coreUserInformationEntity = coreUserInformationEntity;
    }
}
