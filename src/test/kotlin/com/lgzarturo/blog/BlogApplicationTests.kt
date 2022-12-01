package com.lgzarturo.blog

import com.lgzarturo.blog.controllers.Request
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.test.web.client.postForObject
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class BlogApplicationTests(@Autowired private val restTemplate: TestRestTemplate) {

	@Test
	fun testHomeController_indexWithoutParams() {
        val content = """{"id":null,"status":200,"name":null,"age":null,"message":"Hola mundo !","isActive":false}"""
        assertThat(restTemplate.getForObject<String>("/")).isEqualTo(content)
	}

    @Test
	fun testHomeController_indexWithParams() {
        val name = "mágico"
        val params = HashMap<String, String>()
        params["name"] = name
        val content = """{"id":null,"status":200,"name":null,"age":null,"message":"Hola mundo $name","isActive":false}"""
        assertThat(restTemplate.getForObject<String>("/?name={name}", params)).isEqualTo(content)
	}

    @Test
    fun testHomeController_indexWithBody() {
        val request = Request().apply { name = "John" }.apply { age = 40 }
        val content = """{"id":null,"status":200,"name":null,"age":null,"message":"Hola mundo, tenemos un mensaje de ${request.name} y tiene ${request.age} años de antigüedad.","isActive":false}"""
        assertThat(restTemplate.postForObject<String>("/", request)).isEqualTo(content)
    }

}
