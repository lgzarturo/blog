package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.dtos.UserChangePasswordRequest
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthController(private val userService: UserService) {
    @PostMapping("/login")
    fun login(): String = "Login ok"

    @PostMapping("/register")
    fun register(@Valid @RequestBody userRegister: UserRegisterRequest): User = userService.registerUser(userRegister)

    @PostMapping("/update/password")
    fun updatePassword(@Valid @RequestBody userChangePassword: UserChangePasswordRequest): User = userService.changePassword(userChangePassword)
}
