package com.lgzarturo.blog.component

import com.lgzarturo.blog.models.entities.Role
import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.repositories.RoleRepository
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
class DevelopmentInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
    ) : ApplicationRunner {

    private final val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Insertando usuario en la base de datos con los repositorios
     * `UserRepository` y `RoleRepository`
     */
    override fun run(args: ApplicationArguments?) {
        logger.info("Starting Development Initializer")
        logger.info("================================")
        roleRepository.save(Role(authority = "USER"))
        val roles = HashSet<Role>()
        roles.add(roleRepository.findByAuthority("USER").get())
        val user = User()
        user.email = "admin@example.com"
        user.password = "super-secret-password"
        user.authorities = roles
        userRepository.save(user)
        logger.info("Hay ${userRepository.count()} numero de usuarios en la base...")
    }
}
