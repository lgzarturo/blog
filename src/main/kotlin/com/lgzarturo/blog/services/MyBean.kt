package com.lgzarturo.blog.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MyBean {
    private val logger: Logger = LoggerFactory.getLogger(MyBean::class.java)

    fun greetings() {
        logger.info("Saludando desde el my-bean")
    }
}
