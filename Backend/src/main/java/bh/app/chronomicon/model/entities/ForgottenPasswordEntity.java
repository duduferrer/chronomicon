package bh.app.chronomicon.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ForgottenPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "sys_user_id")
    private SystemUserEntity systemUserEntity;

    private String passwordRefreshToken;

    private LocalDateTime expiresAt;

    private boolean used = false;

    public ForgottenPasswordEntity() {
    }

    public ForgottenPasswordEntity(String id, SystemUserEntity systemUserEntity, String passwordRefreshToken, LocalDateTime expiresAt, boolean used) {
        this.id = id;
        this.systemUserEntity = systemUserEntity;
        this.passwordRefreshToken = passwordRefreshToken;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public ForgottenPasswordEntity(SystemUserEntity systemUserEntity, String passwordRefreshToken, LocalDateTime expiresAt, boolean used) {
        this.systemUserEntity = systemUserEntity;
        this.passwordRefreshToken = passwordRefreshToken;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public String getId() {
        return id;
    }

    public SystemUserEntity getSystemUserEntity() {
        return systemUserEntity;
    }

    public void setSystemUserEntity(SystemUserEntity systemUserEntity) {
        this.systemUserEntity = systemUserEntity;
    }

    public String getPasswordRefreshToken() {
        return passwordRefreshToken;
    }

    public void setPasswordRefreshToken(String passwordRefreshToken) {
        this.passwordRefreshToken = passwordRefreshToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
