package com.lgzarturo.blog.component

import com.google.api.services.gmail.Gmail
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

/**
 * Una forma para ejecutar código al iniciar el proyecto,
 * se pueden inyectar las dependencias necesarias en el método
 * con el nombre `run()`, este es un componente con un bean que
 * se ejecuta al terminar el arranque de Spring.
 */
@Component
class StartProject {

    private final val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Insertando usuario por medio del servicio `UserService`
     */
    @Bean
    fun run(userService: UserService, gmail: Gmail) = CommandLineRunner {
        logger.info("Starting Blog project")
        logger.info("================================")
        val userRequest = UserRegisterRequest()
        userRequest.email = "lgzarturo@gmail.com"
        userRequest.password = "one-password"
        userService.registerUser(userRequest)
        logger.info("Hay ${userService.countUsers()} numero de usuarios en la base...")
        logger.info("GMAIL ${gmail.applicationName}")
    }
}
