package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Role
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    private var role: Role? = null

    @BeforeEach
    fun setUp() {
        role = Role(authority = "ROLE_ADMIN")
    }

    @Test
    fun itShouldFindByAuthority() {
        val role2 = Role(authority = "ROLE_USER")
        roleRepository.save(this.role!!)
        roleRepository.save(role2)
        val persistedRole = roleRepository.findByAuthority("ROLE_USER")
        Assertions.assertThat(persistedRole.isPresent).isTrue
        Assertions.assertThat(persistedRole.get().id).isEqualTo(role2.id)
    }

    @Test
    fun itShouldFailFindByAuthority() {
        roleRepository.save(this.role!!)
        val persistedRole = roleRepository.findByAuthority("ROLE_USER")
        Assertions.assertThat(persistedRole.isPresent).isFalse
    }
}
