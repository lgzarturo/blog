package com.lgzarturo.blog.entities

import com.lgzarturo.blog.common.UserType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 1)
    val id: Long? = null,
    val email: String? = null,
    val password: String? = null,
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    val userType: UserType? = null,
    val isActive: Boolean? = null,
    @Transient
    val author: Author? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
