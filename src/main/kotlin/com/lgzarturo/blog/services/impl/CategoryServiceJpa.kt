package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.dtos.CategoryRequest
import com.lgzarturo.blog.models.entities.Category
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.repositories.CategoryRepository
import com.lgzarturo.blog.services.CategoryService
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryServiceJpa(
    private val categoryRepository: CategoryRepository,
    private val modelMapper: ModelMapper
) : CategoryService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun getPostsByCategory(id: Long): List<Post> {
        val category = findById(id).orElse(null) ?: return emptyList()
        return category.posts
    }

    override fun getById(id: Long): Category? {
        return findById(id).orElse(null)
    }

    override fun save(categoryRequest: CategoryRequest): Category? {
        val category = modelMapper.map(categoryRequest, Category::class.java)
        return try {
            categoryRepository.save(category)
        } catch (e: Exception) {
            log.error("Error saving category ${e.message}")
            null
        }
    }

    override fun update(id: Long, categoryRequest: CategoryRequest): Category? {
        val categoryPersisted = findById(id).orElse(null) ?: return null
        BeanUtils.copyProperties(categoryRequest, categoryPersisted)
        return categoryRepository.save(categoryPersisted)
    }

    override fun delete(id: Long) {
        try {
            val category = findById(id).get()
            categoryRepository.delete(category)
        } catch (error: Exception) {
            val message = "Error al intentar eliminar el registro con el id:$id"
            throw Exception(message)
        }
    }

    private fun findById(id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }
}
