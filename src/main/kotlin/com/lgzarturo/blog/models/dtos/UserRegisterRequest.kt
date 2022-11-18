package com.lgzarturo.blog.models.dtos

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserRegisterRequest {
    @Email
    var email: String? = null
    @NotBlank
    var password: String? = null
}
