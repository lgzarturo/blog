package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.dtos.UserChangePasswordRequest
import com.lgzarturo.blog.models.dtos.UserRegisterRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.patchForObject
import org.springframework.boot.test.web.client.postForObject
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class AuthControllerTest(@Autowired private val restTemplate: TestRestTemplate) {

    @Test
    fun itShouldTestSuccessLogin() {
        val content = "Login ok"
        Assertions.assertThat(restTemplate.postForObject<String>("/auth/login")).isEqualTo(content)
    }

    @Test
    fun itShouldUserRegister_thenReturnUserWithoutPassword() {
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
    fun itShouldRegisterWithoutEmail_thenReturnBadRequest() {
        val password = "password-super-complex"
        val request = UserRegisterRequest()
        request.password = password
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("Bad Request")
        Assertions.assertThat(result).contains("NotBlank.userRegisterRequest.email")
    }

    @Test
    fun itShouldRegisterWithoutPassword_thenReturnBadRequest() {
        val email = "testWithoutPass@example.com"
        val request = UserRegisterRequest()
        request.email = email
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("Bad Request")
        Assertions.assertThat(result).contains("NotBlank.userRegisterRequest.password")
    }

    @Test
    fun itShouldRegisterWithEmailAlreadyRegistered_thenReturnMessageError() {
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
    fun itShouldRegisterWithEmailMalformed_thenReturnBadRequest() {
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
    fun itShouldRegisterWithPasswordIsToShort_thenReturnMessageError() {
        val password = "pc"
        val email = "testPassToShort@example.com"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains("Size.password")
        Assertions.assertThat(result).contains(""""field":"password","rejectedValue":"pc","bindingFailure":false,"code":"Size"""")
    }

    @Test
    fun itShouldTestUpdatePassword_thenReturnUserWithoutPassword() {
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
    fun itShouldUpdatePasswordWithUserNotExists_thenReturnErrorMessage() {
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
    fun itShouldUpdatePasswordWithConfirmPasswordNotMatch_thenReturnErrorMessage() {
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

    @Test
    @Disabled
    fun itShouldTestGenerateVerificationCode() {
        val password = "super-password"
        val email = "lgzarturo@gmail.com"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        restTemplate.postForObject<String>("/auth/register", request)
        val params = HashMap<String, String>()
        params["email"] = email
        val result = restTemplate.postForObject<String>("/auth/email/code", params)
        Assertions.assertThat(result).contains("Verification code generated, email sent")
    }

    @Test
    fun itShouldGenerateVerificationCodeWithUsernameNotExists_thenReturnErrorMessage() {
        val params = HashMap<String, String>()
        params["email"] = "one-email-not-exist@gmail.com"
        val result = restTemplate.postForObject<String>("/auth/email/code", params)
        Assertions.assertThat(result).contains("User does not exist")
    }

    @Test
    fun itShouldGenerateVerificationCodeWithUsernameNotProvided_thenReturnErrorMessage() {
        val params = HashMap<String, String>()
        val result = restTemplate.postForObject<String>("/auth/email/code", params)
        Assertions.assertThat(result).contains("Param email is required")
    }


    @Test
    fun testValidationVerificationCode_usernameNotProvided() {
        val params = HashMap<String, String>()
        params["code"] = "11291292"
        val result = restTemplate.postForObject<String>("/auth/email/code/verification", params)
        Assertions.assertThat(result).contains("Param email is required")
    }

    @Test
    fun itShouldValidationVerificationCodeWithCodeNotProvided_thenReturnErrorMessage() {
        val params = HashMap<String, String>()
        params["email"] = "one@gmail.com"
        val result = restTemplate.postForObject<String>("/auth/email/code/verification", params)
        Assertions.assertThat(result).contains("Param code is required")
    }

    @Test
    fun itShouldValidationVerificationCodeWithErrorCodeInvalid_thenReturnErrorMessage() {
        val params = HashMap<String, String>()
        val code = "11291292"
        params["email"] = "lgzarturo@gmail.com"
        params["code"] = code
        val result = restTemplate.postForObject<String>("/auth/email/code/verification", params)
        Assertions.assertThat(result).contains("User verification failed")
    }

    @Test
    fun itShouldTestChangePassword() {
        val email = "changepassword@example.com"
        val password = "password-super-complex"
        val request = UserRegisterRequest()
        request.email = email
        request.password = password
        restTemplate.postForObject<String>("/auth/register", request)
        val params = HashMap<String, String>()
        params["password"] = "11291292"
        params["email"] = email
        val result = restTemplate.patchForObject<String>("/auth/update/password", params)
        Assertions.assertThat(result).contains("Password updated successfully")
    }

    @Test
    fun itShouldChangePasswordWithUsernameNotProvided_thenReturnErrorMessage() {
        val params = HashMap<String, String>()
        params["password"] = "11291292"
        val result = restTemplate.patchForObject<String>("/auth/update/password", params)
        Assertions.assertThat(result).contains("Param email is required")
    }

    @Test
    fun itShouldChangePasswordWithPasswordNotProvided_thenReturnErrorMessage() {
        val params = HashMap<String, String>()
        params["email"] = "one@gmail.com"
        val result = restTemplate.patchForObject<String>("/auth/update/password", params)
        Assertions.assertThat(result).contains("Param password is required")
    }
}
