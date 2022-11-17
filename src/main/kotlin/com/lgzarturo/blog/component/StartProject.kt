package com.lgzarturo.blog.component

import com.lgzarturo.blog.repositories.UserRepository
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

    @Bean
    fun run(userRepository: UserRepository) = CommandLineRunner {
        logger.info("Starting Blog project")
        logger.info("================================")
        logger.info("Hay ${userRepository.count()} numero de usuarios en la base...")
    }
}
