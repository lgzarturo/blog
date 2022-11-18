package com.lgzarturo.blog.services

import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.models.entities.User

interface UserService {
    fun registerUser(userRegister: UserRegisterRequest): User
    fun registerAdmin(userRegister: UserRegisterRequest): User
    fun countUsers(): Long
}
