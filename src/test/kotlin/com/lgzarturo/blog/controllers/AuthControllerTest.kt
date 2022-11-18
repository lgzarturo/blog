package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.dtos.UserChangePasswordRequest
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class AuthControllerTest(@Autowired private val restTemplate: TestRestTemplate) {

    @Test
    fun testLogin() {
        val content = "Login ok"
        Assertions.assertThat(restTemplate.postForObject<String>("/auth/login")).isEqualTo(content)
    }

    @Test
    fun testRegister() {
        val email = "testRegister@example.com"
        val password = "password-super-complex"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains(email.lowercase())
        Assertions.assertThat(result).contains(""""authority":"USER"""")
        Assertions.assertThat(result).contains(""""isActive":true""")
        Assertions.assertThat(result).doesNotContain(password)
    }

    @Test
    fun testRegister_withoutEmail() {
        val password = "password-super-complex"
        val request = UserRegisterRequest()
        request.password = password
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("Bad Request")
        Assertions.assertThat(result).contains("NotBlank.userRegisterRequest.email")
    }

    @Test
    fun testRegister_withoutPassword() {
        val email = "testWithoutPass@example.com"
        val request = UserRegisterRequest()
        request.email = email
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("Bad Request")
        Assertions.assertThat(result).contains("NotBlank.userRegisterRequest.password")
    }

    @Test
    fun testRegister_emailAlreadyRegistered() {
        val password = "password-super-complex"
        val email = "testAllReady@example.com"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        restTemplate.postForObject<String>("/auth/register", request)
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("The email already registered")
    }

    @Test
    fun testRegister_emailMalformed() {
        val password = "password-super-complex"
        val email = "testAllReady"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        restTemplate.postForObject<String>("/auth/register", request)
        val result = restTemplate.postForObject<String>("/auth/register", request)
        println(result)
        Assertions.assertThat(result).contains("Bad Request")
        Assertions.assertThat(result).contains("must be a well-formed email address")
    }

    @Test
    fun testRegister_passwordIsToShort() {
        val password = "pc"
        val email = "testPassToShort@example.com"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("User registration failed")
    }

    @Test
    fun testUpdatePassword() {
        val password = "super-password"
        val nuevoPassword = "new-password-xyz"
        val email = "testupdatepassword@example.com"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        val changePasswordRequest = UserChangePasswordRequest()
        changePasswordRequest.email = email
        changePasswordRequest.password = nuevoPassword
        changePasswordRequest.confirmPassword = nuevoPassword
        restTemplate.postForObject<String>("/auth/register", request)
        val result = restTemplate.postForObject<String>("/auth/update/password", changePasswordRequest)
        Assertions.assertThat(result).contains(email.lowercase())
        Assertions.assertThat(result).contains(""""authority":"USER"""")
        Assertions.assertThat(result).contains(""""isActive":true""")
        Assertions.assertThat(result).doesNotContain(password)
    }

    @Test
    fun testUpdatePassword_userNotExists() {
        val password = "super-password"
        val email = "testusernotexists@example.com"
        val request = UserChangePasswordRequest()
        request.email = email
        request.password = password
        request.confirmPassword = password
        val result = restTemplate.postForObject<String>("/auth/update/password", request)
        Assertions.assertThat(result).contains("User does not exist")
    }

    @Test
    fun testUpdatePassword_confirmPasswordNotMatch() {
        val password = "one-password-abc"
        val nuevoPassword = "new-password-xyz"
        val email = "testconfirmpass@example.com"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        val changePasswordRequest = UserChangePasswordRequest()
        changePasswordRequest.email = email
        changePasswordRequest.password = nuevoPassword
        changePasswordRequest.confirmPassword = "T$nuevoPassword"
        restTemplate.postForObject<String>("/auth/register", request)
        val result = restTemplate.postForObject<String>("/auth/update/password", changePasswordRequest)
        Assertions.assertThat(result).contains("Password and confirmation must be match")
    }
}
