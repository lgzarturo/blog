package com.lgzarturo.blog.models.entities

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(nullable = false)
    val authority: String? = null
) : BaseEntity() {
    override fun toString(): String {
        return "Role(authority=$authority)"
    }
}
