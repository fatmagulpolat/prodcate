package com.prodcate.prodcate.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdDate;

    @CreatedBy
    String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime updatedDate;

    @LastModifiedBy
    String updatedBy;
}
