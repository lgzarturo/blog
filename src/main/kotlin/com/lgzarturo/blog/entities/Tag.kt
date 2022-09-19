package com.lgzarturo.blog.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "tags", uniqueConstraints=[UniqueConstraint(columnNames=["name", "slug"])])
class Tag (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @NotBlank
    val name: String? = null,
    @NotBlank
    val slug: String? = null,
    @ManyToMany(mappedBy = "tags")
    val posts: MutableSet<Post> = mutableSetOf()
) : BaseEntity() {
    override fun toString(): String {
        return "Tag(name=$name, slug=$slug)"
    }
}
