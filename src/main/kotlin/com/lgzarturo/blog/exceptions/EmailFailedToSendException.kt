package com.lgzarturo.blog.exceptions

class EmailFailedToSendException(
    override val message: String? = "The email failed to send"
) : RuntimeException() {
    private val serialVersionUID = 1L
}
