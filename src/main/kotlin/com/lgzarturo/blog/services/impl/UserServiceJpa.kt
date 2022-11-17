package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.entities.Role
import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.repositories.RoleRepository
import com.lgzarturo.blog.repositories.UserRepository
import com.lgzarturo.blog.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceJpa(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
    ) : UserService {

    override fun register(user: User): User {
        val roles = HashSet<Role>()
        roles.add(roleRepository.findByAuthority("USER").get())
        user.authorities = roles
        return userRepository.save(user)
    }

    override fun countUsers(): Long {
        return userRepository.count()
    }
}
