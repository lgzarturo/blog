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
    val parentId: Long? = null,
    val postId: Long? = null,
    val author: String? = null,
    val authorEmail: String? = null,
    val ipAddress: String? = null,
    val content: String? = null,
    val isApproved: Boolean? = null,
    val commentLikes: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
