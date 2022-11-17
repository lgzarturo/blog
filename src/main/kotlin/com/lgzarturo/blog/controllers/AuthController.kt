package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.User
import com.lgzarturo.blog.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthController(private val userService: UserService) {
    @PostMapping("/login")
    fun login(): String = "Login ok"

    @PostMapping("/register")
    fun register(@RequestBody user: User): User = userService.register(user)
}
