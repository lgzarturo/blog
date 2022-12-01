package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun countByEmail(email: String): Long
}
