package com.lgzarturo.blog.exceptions

class UserDoesNotExistException(
    override val message: String? = "User does not exist"
) : RuntimeException() {
    private val serialVersionUID = 1L
}
