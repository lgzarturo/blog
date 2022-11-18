package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.exceptions.EmailAlreadyRegisteredException
import com.lgzarturo.blog.exceptions.RegistrationUserException
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.models.entities.Role
import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.repositories.RoleRepository
import com.lgzarturo.blog.repositories.UserRepository
import com.lgzarturo.blog.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceJpa(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
    ) : UserService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun registerUser(userRegister: UserRegisterRequest): User {
        val roles = HashSet<Role>()
        roles.add(roleRepository.findByAuthority("USER").get())
        return register(userRegister, roles)
    }

    override fun registerAdmin(userRegister: UserRegisterRequest): User {
        val roles = HashSet<Role>()
        roles.add(roleRepository.findByAuthority("ADMIN").get())
        return register(userRegister, roles)
    }

    private fun register(userRegister: UserRegisterRequest, roles: HashSet<Role>): User {
        val user = User()
        user.email = userRegister.email!!.trim().lowercase()
        user.password = userRegister.password!!.trim()
        user.authorities = roles
        if (userRepository.countByEmail(user.email!!) > 0) {
            throw EmailAlreadyRegisteredException()
        }
        try {
            return userRepository.save(user)
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("No se pudo registrar el usuario ${e.message}")
            throw RegistrationUserException()
        }
    }

    override fun countUsers(): Long {
        return userRepository.count()
    }
}
