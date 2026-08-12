package com.nanda.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@MappedSuperclass @Getter @Setter
public abstract class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @CreationTimestamp @Column(nullable=false, updatable=false) private Instant createdAt;
    @UpdateTimestamp @Column(nullable=false) private Instant updatedAt;
    @Column(nullable=false) private boolean deleted = false;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
    public boolean isDeleted(){return deleted;} public void setDeleted(boolean deleted){this.deleted=deleted;}
}
