package com.lgzarturo.blog.models.entities

import com.fasterxml.jackson.annotation.JsonBackReference
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
    @JsonBackReference
    @ManyToMany(mappedBy = "tags")
    val posts: MutableSet<Post> = mutableSetOf()
) : BaseEntity() {
    override fun toString(): String {
        return "Tag(name=$name, slug=$slug)"
    }
}
