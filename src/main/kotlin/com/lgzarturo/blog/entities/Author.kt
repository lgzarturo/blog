package com.lgzarturo.blog.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "authors")
class Author (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorSeqGen")
    @SequenceGenerator(name = "authorSeqGen", sequenceName = "authorSeq", initialValue = 1)
    val id: Long? = null,
    @NotBlank
    @Column(length = 90)
    val name: String? = null,
    val avatarImage: String? = null,
    @Column(length = 1000)
    val description: String? = null,
    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val posts: List<Post> = emptyList()
) : BaseEntity() {
    override fun toString(): String {
        return "Author(name=$name)"
    }
}
