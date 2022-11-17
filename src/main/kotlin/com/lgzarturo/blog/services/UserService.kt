package com.lgzarturo.blog.services

import com.lgzarturo.blog.models.entities.User

interface UserService {
    fun register(user: User): User
    fun countUsers(): Long
}
