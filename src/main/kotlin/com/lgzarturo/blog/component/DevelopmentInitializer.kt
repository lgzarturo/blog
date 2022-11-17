package com.lgzarturo.blog.component

import com.lgzarturo.blog.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

/**
 * Este componente usa ApplicationRunner, como otra forma para ejecutar
 * código al iniciar el proyecto.
 *
 * Aquí se presenta @Profile como una forma de especificarle al runner
 * perfil si se va a ejecutar la función `run()`
 *
 * Con el constructor se pueden inyectar las dependencias necesarias.
 */
@Component
@Profile("dev")
class DevelopmentInitializer(private val userRepository: UserRepository) : ApplicationRunner {
    private final val logger = LoggerFactory.getLogger(this::class.java)

    override fun run(args: ApplicationArguments?) {
        logger.info("Starting Development Initializer")
        logger.info("================================")
        logger.info("Hay ${userRepository.count()} numero de usuarios en la base...")
    }
}
