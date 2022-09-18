package com.lgzarturo.blog.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "authors")
class Author (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorSeqGen")
    @SequenceGenerator(name = "authorSeqGen", sequenceName = "authorSeq", initialValue = 1)
    val id: Long? = null,
    @Column(length = 90)
    val name: String? = null,
    val avatarImage: String? = null,
    @Column(length = 1000)
    val description: String? = null,
    @Transient
    val posts: List<Post> = emptyList(),
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
