package com.lgzarturo.blog.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.lgzarturo.blog.bodyTo
import com.lgzarturo.blog.models.entities.Category
import com.lgzarturo.blog.services.CategoryService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class CategoryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var categoryService: CategoryService

    @Test
    fun list_allCategories() {
        val categoriesFromService = categoryService.getAll().map { it.id }
        val categories = mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .bodyTo<List<Category>>(mapper)
        assertThat(categoriesFromService, Matchers.`is`(Matchers.equalTo(categories.map { it.id })))
    }
}
