package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.exceptions.*
import com.lgzarturo.blog.models.dtos.UserChangePasswordRequest
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.models.entities.Role
import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.repositories.RoleRepository
import com.lgzarturo.blog.repositories.UserRepository
import com.lgzarturo.blog.services.MailService
import com.lgzarturo.blog.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.math.floor

@Service
class JpaUserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val mailService: MailService,
    private val passwordEncoder: PasswordEncoder
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
            user.password = passwordEncoder.encode(userChangePassword.password)
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
        user.password = passwordEncoder.encode(userRegister.password!!.trim())
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
        mailService.sendEmail(
            email,
            "Blog - Registro de usuario",
            """
                Datos del usuario
                - Correo electrónico: $email
                - Código para verificar la cuenta: $verificationCode
                
                Nota: 
                Cuando el sistema se lo pida, favor de proporcionar este código para activar la cuenta.                
            """.trimIndent()
        )
        userRepository.save(user)
        return verificationCode
    }

    override fun verificationCode(code: String, email: String): User {
        val user = userRepository.findByEmail(email).orElseThrow { UserDoesNotExistException() }
        try {
            if (user.verification?.equals(code.toLong()) == true) {
                user.enabled = true
                user.verification = null
                return userRepository.save(user)
            } else {
                throw VerificationUserException("The code '$code' did not match with the user verification code")
            }
        } catch (e: Exception) {
            log.error("Error al intentar parsear el código '$code'. Error: ${e.message}")
        }
        throw VerificationUserException()
    }

    override fun updatePassword(email: String, password: String) {
        val user = userRepository.findByEmail(email).orElseThrow { UserDoesNotExistException() }
        user.password = passwordEncoder.encode(password)
        try {
            userRepository.save(user)
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("No se pudo cambiar la contraseña ${e.message}")
            throw PasswordEncoderException()
        }
    }

    private fun generateVerificationCode(): Long {
        return floor(Math.random() * 1_000_000_000).toLong()
    }
}
