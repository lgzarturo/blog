package com.lgzarturo.blog.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.validator.constraints.URL
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "authors")
class Author : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorSeqGen")
    @SequenceGenerator(name = "authorSeqGen", sequenceName = "authorSeq", initialValue = 1)
    val id: Long? = null
    @NotBlank
    @Size(min = 3, max = 90)
    @Column(length = 90)
    var name: String? = null
    @URL
    var avatarImage: String? = null
    @Size(max = 1_000)
    @Column(length = 1_000)
    var description: String? = null
    @JsonManagedReference
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val posts: List<Post> = emptyList()

    override fun toString(): String {
        return "Author(name=$name)"
    }
}
