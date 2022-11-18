package com.lgzarturo.blog.exceptions

class RegistrationUserException(
    override val message: String? = "User registration failed",
) : RuntimeException() {
    private val serialVersionUID = 1L
}
