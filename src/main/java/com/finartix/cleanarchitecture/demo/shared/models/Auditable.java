package com.finartix.cleanarchitecture.demo.shared.models;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class Auditable<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(updatable = false)
    protected T createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TIMESTAMP)
    protected Instant createdDate;

    @LastModifiedBy
    @Column(nullable = false)
    protected T lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Instant lastModifiedDate;
}
