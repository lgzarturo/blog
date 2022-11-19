package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.exceptions.ConfirmationPasswordNotMatchException
import com.lgzarturo.blog.exceptions.EmailAlreadyRegisteredException
import com.lgzarturo.blog.exceptions.RegistrationUserException
import com.lgzarturo.blog.exceptions.UserDoesNotExistException
import com.lgzarturo.blog.models.dtos.UserChangePasswordRequest
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.models.entities.Role
import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.repositories.RoleRepository
import com.lgzarturo.blog.repositories.UserRepository
import com.lgzarturo.blog.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.floor

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

    override fun changePassword(userChangePassword: UserChangePasswordRequest): User {
        val user = userRepository.findByEmail(userChangePassword.email!!).orElseThrow { UserDoesNotExistException() }
        if (userChangePassword.password != userChangePassword.confirmPassword) {
            throw ConfirmationPasswordNotMatchException()
        }
        try {
            user.password = userChangePassword.password
            return userRepository.save(user)
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("No se pudo registrar el usuario ${e.message}")
            throw RegistrationUserException()
        }
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

    override fun generateEmailVerificationCode(email: String): Long {
        val user = userRepository.findByEmail(email).orElseThrow { UserDoesNotExistException() }
        val verificationCode = generateVerificationCode()
        user.verification = verificationCode
        return verificationCode
    }

    private fun generateVerificationCode(): Long {
        return floor(Math.random() * 1_000_000_000).toLong()
    }
}
