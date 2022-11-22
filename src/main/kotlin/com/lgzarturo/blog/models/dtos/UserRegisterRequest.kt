package com.lgzarturo.blog.models.dtos

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserRegisterRequest {
    @Email
    @NotBlank
    var email: String? = null
    @NotBlank
    @Size(min = 4)
    var password: String? = null
}
