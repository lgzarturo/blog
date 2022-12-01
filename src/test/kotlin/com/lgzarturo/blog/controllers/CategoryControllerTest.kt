package com.lgzarturo.blog.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lgzarturo.blog.body
import com.lgzarturo.blog.bodyTo
import com.lgzarturo.blog.models.dtos.CategoryRequest
import com.lgzarturo.blog.models.entities.Category
import com.lgzarturo.blog.services.impl.CategoryServiceJpa
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class CategoryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var modelMapper: ModelMapper

    @Autowired
    private lateinit var categoryService: CategoryServiceJpa

    private val endpointUri = "/categories"

    @Test
    fun testList_allCategories() {
        val categoriesFromService = categoryService.all().map { it.id }
        val categories = mockMvc.perform(get(endpointUri))
            .andExpect(status().isOk)
            .bodyTo<List<Category>>(mapper)
        assertThat(categoriesFromService, Matchers.`is`(Matchers.equalTo(categories.map { it.id })))
    }

    @Test
    fun testFinById() {
        val categoriesFromService = categoryService.all()
        assert(categoriesFromService.isNotEmpty())
        val category = categoriesFromService.first()
        mockMvc.perform(get("$endpointUri/${category.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title", Matchers.`is`(category.title)))
    }

    @Test
    fun testFinById_notFound() {
        mockMvc.perform(get("$endpointUri/99181771"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    fun testCreateCategory(): Category {
        val category = Category(title = "Web Development", slug = "web-dev", description = "web development programming")
        val result = mockMvc.perform(post(endpointUri).body(data = category, mapper = mapper))
            .andExpect(status().isCreated)
            .bodyTo<Category>(mapper)
        assertNotNull(result)
        return result
    }

    @Test
    fun testCreateCategory_fail() {
        val categoriesFromService = categoryService.all()
        assert(categoriesFromService.isNotEmpty())
        val category = categoriesFromService.first()
        val categoryRequest = modelMapper.map(category, CategoryRequest::class.java)
        mockMvc.perform(post(endpointUri).body(data = categoryRequest, mapper = mapper))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testUpdateCategory() {
        val categoriesFromService = categoryService.all()
        assert(categoriesFromService.isNotEmpty())
        val category = categoriesFromService.first().apply { title = "Nueva categoría" }
        val result = mockMvc.perform(put("$endpointUri/${category.id}").body(data = category, mapper = mapper))
            .andExpect(status().isOk)
            .bodyTo<Category>(mapper)
        assertNotNull(result)
    }

    @Test
    fun testUpdateCategory_fail() {
        val categoriesFromService = categoryService.all()
        assert(categoriesFromService.isNotEmpty())
        val category = categoriesFromService.first().apply { title = "Nueva categoría" }
        mockMvc.perform(put("$endpointUri/121213").body(data = category, mapper = mapper))
            .andExpect(status().isNotFound)
    }

    @Test
    fun testDeleteCategory() {
        val category = testCreateCategory()
        val result = mockMvc.perform(delete("$endpointUri/${category.id}"))
            .andExpect(status().isNoContent)
        assertNotNull(result)
        assert(!categoryService.all().contains(category))
    }

    @Test
    fun testDeleteCategory_failRelationship() {
        val categoriesFromService = categoryService.all()
        assert(categoriesFromService.isNotEmpty())
        val category = categoriesFromService.first()
        mockMvc.perform(delete("$endpointUri/${category.id}"))
            .andExpect(status().isUnprocessableEntity)
        assert(categoryService.all().map { it.id }.contains(category.id))
    }

    @Test
    fun testDeleteCategory_badRequest() {
        mockMvc.perform(delete("$endpointUri/${UUID.randomUUID()}"))
            .andExpect(status().isBadRequest)
    }
}
