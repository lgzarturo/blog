package com.lgzarturo.blog.entities

import com.lgzarturo.blog.common.PostType
import java.time.LocalDate
import javax.persistence.*


@Entity
@Table(name = "posts")
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGen")
    @SequenceGenerator(name = "postSeqGen", sequenceName = "postSeq", initialValue = 1)
    val id: Long? = null,
    @ManyToOne
    val partentPost: Post? = null,
    @Column(length = 180)
    val title: String? = null,
    @Column(length = 180)
    val slug: String? = null,
    val coverImage: String? = null,
    @Column(length = 1000)
    val summary: String? = null,
    @Column(length = 65535)
    val content: String? = null,
    @Column(length = 20)
    val status: String? = null,
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    val postType: PostType = PostType.ARTICLE,
    val postLikes: Long = 0L,
    val postComments: Long = 0L,
    val hasPage: Boolean = false,
    val menuOrder: Int = 0,
    @ManyToOne
    val author: Author? = null,
    @ManyToOne
    val category: Category? = null,
    val publishedAt: LocalDate? = null,
) : BaseEntity()
