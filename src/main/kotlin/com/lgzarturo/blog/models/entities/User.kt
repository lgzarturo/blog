package com.lgzarturo.blog.models.entities

import com.lgzarturo.blog.models.enums.UserType
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 1)
    val id: Long? = null,
    @Email
    @Column(unique = true)
    val email: String? = null,
    @NotBlank
    val password: String? = null,
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    val userType: UserType = UserType.USER,
    val isActive: Boolean = true,
    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    val author: Author? = null,
) : BaseEntity() {
    override fun toString(): String {
        return "User(email=$email)"
    }
}
