package com.lgzarturo.blog.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "comments")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeqGen")
    @SequenceGenerator(name = "commentSeqGen", sequenceName = "commentSeq", initialValue = 1)
    val id: Long? = null,
    @ManyToOne
    val parentComment: Comment? = null,
    @NotNull
    @ManyToOne
    val post: Post? = null,
    @NotBlank
    @Column(length = 90)
    val author: String? = null,
    @NotBlank
    val authorEmail: String? = null,
    @Column(length = 100)
    val ipAddress: String? = null,
    @NotBlank
    @Column(length = 1000)
    val content: String? = null,
    val isApproved: Boolean = false,
    val commentLikes: Long = 0L,
) : BaseEntity()
