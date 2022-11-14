package com.lgzarturo.blog.models.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "authors")
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorSeqGen")
    @SequenceGenerator(name = "authorSeqGen", sequenceName = "authorSeq", initialValue = 1)
    val id: Long? = null,
    @Column(length = 90, nullable = false)
    var name: String? = null,
    var avatarImage: String? = null,
    @Column(length = 1_000)
    var description: String? = null,
    @JsonManagedReference
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val posts: List<Post> = emptyList()
) : BaseEntity() {
    override fun toString(): String {
        return "Author(name=$name)"
    }
}
