package com.lgzarturo.blog.exceptions

import java.time.Instant

class ErrorException(ex: RuntimeException) {
    var message: String? = null
    val date: Instant = Instant.now()

    init {
        message = ex.message!!
    }
}
