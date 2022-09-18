package com.lgzarturo.blog.entities

import com.lgzarturo.blog.common.UserType
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
    val userType: UserType = UserType.USER,
    val isActive: Boolean = true,
    @OneToOne
    val author: Author? = null,
) : BaseEntity()
