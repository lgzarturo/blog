package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByAuthority(authority: String): Optional<Role>
}
