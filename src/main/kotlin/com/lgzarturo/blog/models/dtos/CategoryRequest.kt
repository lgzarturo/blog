package com.lgzarturo.blog.models.dtos

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class CategoryRequest {
    @NotBlank
    var title: String? = null
    @Size(max = 1_000)
    var description: String? = null
}
