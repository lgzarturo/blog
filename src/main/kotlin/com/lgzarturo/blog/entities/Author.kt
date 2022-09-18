package com.lgzarturo.blog.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
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
    @OneToMany
    val posts: List<Post> = emptyList(),
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null
)
