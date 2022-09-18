package com.lgzarturo.blog.entities

import javax.persistence.*

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeqGen")
    @SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 1)
    val id: Long? = null,
    @Column(length = 180)
    val title: String? = null,
    @Column(length = 180)
    val slug: String? = null,
    @Column(length = 1000)
    val description: String? = null,
    @OneToMany
    val posts: List<Post> = emptyList()
) : BaseEntity()
