package com.lgzarturo.blog.entities

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "likes_counter")
class LikeCounter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @NotNull
    @Column(length = 10)
    val contentType: String? = null,
    @NotNull
    val contentId: Long? = null,
    val likesNumber: Long = 0L,
) : BaseEntity() {
    override fun toString(): String {
        return "LikeCounter(contentType=$contentType, contentId=$contentId)"
    }
}
