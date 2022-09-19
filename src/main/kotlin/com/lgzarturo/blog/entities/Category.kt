package com.lgzarturo.blog.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeqGen")
    @SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 1)
    val id: Long? = null,
    @NotBlank
    @Column(length = 180)
    val title: String? = null,
    @NotBlank
    @Column(length = 180)
    val slug: String? = null,
    @Column(length = 1000)
    val description: String? = null,
    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val posts: List<Post> = emptyList()
) : BaseEntity() {
    override fun toString(): String {
        return "Category(title=$title)"
    }
}
