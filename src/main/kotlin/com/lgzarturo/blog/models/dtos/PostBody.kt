package com.lgzarturo.blog.models.dtos

import com.lgzarturo.blog.models.entities.*
import com.lgzarturo.blog.models.enums.PostType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PostBody {
    val id: Long? = null
    val parentPost: Post? = null
    @NotBlank
    val title: String? = null
    @NotBlank
    val slug: String? = null
    val coverImage: String? = null
    val summary: String? = null
    val content: String? = null
    val status: String? = null
    val postType: PostType? = null
    val hasPage: Boolean = false
    val menuOrder: Int? = null
    @NotNull
    val author: Author? = null
    @NotNull
    val category: Category? = null
    val comments: List<Comment> = emptyList()
    val tags: MutableSet<Tag> = mutableSetOf()
}
