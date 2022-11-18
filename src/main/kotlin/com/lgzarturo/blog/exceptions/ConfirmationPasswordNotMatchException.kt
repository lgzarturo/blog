package com.lgzarturo.blog.exceptions

class ConfirmationPasswordNotMatchException(
    override val message: String? = "Password and confirmation must be match"
) : RuntimeException() {
    private val serialVersionUID = 1L
}
