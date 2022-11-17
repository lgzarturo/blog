package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.User
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
        val email = "test@example.com"
        val password = "password-super-complex"
        val request = User(email = email, password = password)
        val result = restTemplate.postForObject<String>("/auth/register", request)
        Assertions.assertThat(result).contains(email)
        Assertions.assertThat(result).contains(""""authority":"USER"""")
        Assertions.assertThat(result).contains(""""isActive":true""")
        Assertions.assertThat(result).doesNotContain(password)
    }
}
