package com.lgzarturo.blog.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeqGen")
    @SequenceGenerator(name = "commentSeqGen", sequenceName = "commentSeq", initialValue = 1)
    val id: Long? = null,
    @ManyToOne
    val parentComment: Comment? = null,
    @ManyToOne
    val post: Post? = null,
    @Column(length = 90)
    val author: String? = null,
    val authorEmail: String? = null,
    @Column(length = 100)
    val ipAddress: String? = null,
    @Column(length = 1000)
    val content: String? = null,
    val isApproved: Boolean? = null,
    val commentLikes: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
