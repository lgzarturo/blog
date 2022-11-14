package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
