package com.lgzarturo.blog.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.lgzarturo.blog.models.enums.UserType
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
class User: BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 1)
    val id: Long? = null
    @Email
    @NotBlank
    @Column(unique = true)
    var email: String? = null
    @NotBlank
    @Size(min = 4)
    @JsonIgnore
    var password: String? = null
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    var userType: UserType = UserType.USER
    var isActive: Boolean = true
    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    var author: Author? = null
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var authorities: Set<Role> = HashSet()
    var enabled: Boolean = false
    @JsonIgnore
    @Column(nullable = true)
    var verification: Long? = null
    override fun toString(): String {
        return "User(email=$email)"
    }
}
