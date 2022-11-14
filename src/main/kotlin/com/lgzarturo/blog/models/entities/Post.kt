package com.lgzarturo.blog.models.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.lgzarturo.blog.models.commons.PostType
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@Entity
@Table(name = "posts", uniqueConstraints=[UniqueConstraint(columnNames=["title", "slug"])])
class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGen")
    @SequenceGenerator(name = "postSeqGen", sequenceName = "postSeq", initialValue = 1)
    val id: Long? = null,
    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_post_id", nullable = true, referencedColumnName = "id")
    val parentPost: Post? = null,
    @NotBlank
    @Column(length = 180)
    val title: String? = null,
    @NotBlank
    @Column(length = 180)
    val slug: String? = null,
    val coverImage: String? = null,
    @Column(length = 1000)
    val summary: String? = null,
    @Column(length = 65535)
    val content: String? = null,
    @Column(length = 20)
    val status: String? = null,
    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    val postType: PostType = PostType.ARTICLE,
    val postLikes: Long = 0L,
    val postComments: Long = 0L,
    val hasPage: Boolean = false,
    val menuOrder: Int = 0,
    @JsonBackReference
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false, referencedColumnName = "id")
    val author: Author? = null,
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    val category: Category? = null,
    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val comments: List<Comment> = emptyList(),
    val publishedAt: LocalDate? = null,
    @JsonManagedReference
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(name = "post_tags",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")])
    val tags: MutableSet<Tag> = mutableSetOf()
) : BaseEntity() {
    override fun toString(): String {
        return "Post(title=$title)"
    }
}
