package com.lgzarturo.blog.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
open class BaseEntity (
    @Version
    val version: Int? = null,
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @CreatedBy
    val createdBy: String? = null,
    @LastModifiedDate
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,
    @LastModifiedBy
    val updatedBy: String? = null,
    val isDeleted: Boolean = false,
)
