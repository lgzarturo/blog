package com.lgzarturo.blog.models

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class AuthorRequest {
    @NotBlank
    @Size(min = 3, max = 90)
    val name: String? = null
    @URL
    val avatarImage: String? = null
    @Size(max = 1_000)
    val description: String? = null
}
