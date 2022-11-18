package com.lgzarturo.blog.exceptions

class EmailAlreadyRegisteredException(
    override val message: String? = "The email already registered"
) : RuntimeException() {
    private val serialVersionUID = 1L
}
