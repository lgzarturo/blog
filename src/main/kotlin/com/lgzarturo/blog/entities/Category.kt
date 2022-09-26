package com.lgzarturo.blog.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.slugify.Slugify
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "categories", uniqueConstraints=[UniqueConstraint(columnNames=["title", "slug"])])
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeqGen")
    @SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 1)
    val id: Long? = null,
    @Column(length = 180, nullable = false)
    var title: String? = null,
    @Column(length = 180)
    var slug: String? = null,
    @Column(length = 1000)
    var description: String? = null,
    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val posts: List<Post> = emptyList()
) : BaseEntity() {

    @PrePersist
    fun prePersist() {
        this.slug = slug(this.title!!)
    }

    @PreUpdate
    fun preUpdate() {
        this.slug = slug(this.title!!)
    }

    private fun slug(title: String): String {
        val slug = Slugify.builder().locale(Locale.ENGLISH).build()
        return slug.slugify(title)
    }

    override fun toString(): String {
        return "Category(title=$title)"
    }
}
