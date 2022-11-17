package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.entities.Category
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.models.dtos.CategoryRequest
import com.lgzarturo.blog.repositories.CategoryRepository
import com.lgzarturo.blog.services.CategoryService
import org.modelmapper.ModelMapper
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryJpa(
    private val categoryRepository: CategoryRepository,
    private val modelMapper: ModelMapper
) : CategoryService {

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
        return categoryRepository.save(category)
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
