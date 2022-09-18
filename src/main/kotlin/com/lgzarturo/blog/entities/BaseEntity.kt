package com.lgzarturo.blog.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.Version

@MappedSuperclass
open class BaseEntity (
    @Version
    val version: Int? = null,
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Date? = null,
    @CreatedBy
    val createdBy: String? = null,
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    val updatedAt: Date? = null,
    @LastModifiedBy
    val updatedBy: String? = null,
    val isDeleted: Boolean = false
)
