package com.lgzarturo.blog.entities

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "posts")
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGen")
    @SequenceGenerator(name = "postSeqGen", sequenceName = "postSeq", initialValue = 1)
    val id: Long? = null,
    val parentId: Long? = null,
    val title: String? = null,
    val slug: String? = null,
    val coverImage: String? = null,
    val summary: String? = null,
    val content: String? = null,
    val status: String? = null,
    val postType: String? = null,
    val postLikes: Long? = null,
    val postComments: Long? = null,
    val hasPage: Boolean? = null,
    val menuOrder: Int? = null,
    val authorId: Long? = null,
    val categoryId: Long? = null,
    val publishedAt: LocalDate? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
