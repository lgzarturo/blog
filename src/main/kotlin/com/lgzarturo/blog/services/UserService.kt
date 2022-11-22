package com.lgzarturo.blog.services

import com.lgzarturo.blog.models.dtos.UserChangePasswordRequest
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.models.entities.User

interface UserService {
    fun registerUser(userRegister: UserRegisterRequest): User
    fun registerAdmin(userRegister: UserRegisterRequest): User
    fun changePassword(userChangePassword: UserChangePasswordRequest): User
    fun countUsers(): Long
    fun generateEmailVerificationCode(email: String): Long
    fun verificationCode(code: String, email: String): User
    fun updatePassword(email: String, password: String)
}
