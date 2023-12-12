package com.txvinh.aquariux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditableEntity.AuditListener.class)
public class AuditableEntity {
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_date")
    private Instant updatedDate = Instant.now();

    @Column(name = "updated_by")
    private String updatedBy;

    public static class AuditListener {
        private String currentIdentityName() {
            return "System";
        }

        @PrePersist
        private void forCreate(Object entity) {
            if (entity instanceof AuditableEntity auditableEntity) {
                Instant now = Instant.now();
                auditableEntity.createdDate = now;
                auditableEntity.createdBy = currentIdentityName();
                auditableEntity.updatedDate = now;
            }
            
        }

        @PreUpdate
        private void forUpdate(Object entity) {
            if (entity instanceof AuditableEntity auditableEntity) {
                auditableEntity.updatedDate = Instant.now();
                auditableEntity.updatedBy = currentIdentityName();
            }
            
        }
    }
}
