package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    private var user: User? = null

    @BeforeEach
    fun setUp() {
        user = User()
        user!!.email = "arthurolg@gmail.com"
        user!!.password = "1234"
    }

    @Test
    fun itShouldFindByEmail_thenReturnUser() {
        userRepository.save(this.user!!)
        val user = userRepository.findByEmail("arthurolg@gmail.com").get()
        Assertions.assertThat(user).isNotNull
    }

    @Test
    fun itShouldCountByEmail() {
        userRepository.save(this.user!!)
        val numberOfUser = userRepository.countByEmail("arthurolg@gmail.com")
        Assertions.assertThat(numberOfUser).isEqualTo(1)
    }

    @Test
    fun itShouldCountByEmailIfNotExists() {
        userRepository.save(this.user!!)
        val numberOfUser = userRepository.countByEmail("test@gmail.com")
        Assertions.assertThat(numberOfUser).isEqualTo(0)
    }
}
