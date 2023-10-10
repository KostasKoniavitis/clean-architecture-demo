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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TIMESTAMP)
    protected Instant createdDate;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected T createdBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "last_modified_date", nullable = false)
    protected Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    protected T lastModifiedBy;
}
