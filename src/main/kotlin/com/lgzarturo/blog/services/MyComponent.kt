package com.lgzarturo.blog.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MyComponent {
    private val logger: Logger = LoggerFactory.getLogger(MyComponent::class.java)

    fun greetingFromComponent() {
        logger.info("Greeting from MyComponent - greeting from component")
    }
}
