package com.lgzarturo.blog.exceptions

class PasswordEncoderException(
    override val message: String? = "Password encode failed, must be a valid password"
) : RuntimeException() {
    private val serialVersionUID = 1L
}
