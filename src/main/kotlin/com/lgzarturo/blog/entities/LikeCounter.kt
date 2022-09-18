package com.lgzarturo.blog.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "likes_counter")
class LikeCounter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 10)
    val contentType: String? = null,
    val contentId: Long? = null,
    val likesNumber: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
