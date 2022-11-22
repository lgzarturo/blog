package com.lgzarturo.blog.exceptions

class VerificationUserException(
    override val message: String? = "User verification failed"
) : RuntimeException() {
    private val serialVersionUID = 1L
}
