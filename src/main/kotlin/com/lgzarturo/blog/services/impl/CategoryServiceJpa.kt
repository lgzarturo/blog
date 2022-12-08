package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.entities.Category
import com.lgzarturo.blog.repositories.CategoryRepository
import com.lgzarturo.blog.services.BasicCrud
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryServiceJpa(
    private val categoryRepository: CategoryRepository
) : BasicCrud<Category, Long> {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun all(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun create(data: Category): Category? {
        return try {
            categoryRepository.save(data)
        } catch (e: Exception) {
            log.error("Error saving category ${e.message}")
            null
        }
    }

    override fun read(id: Long): Category? {
        return findById(id).orElse(null)
    }


    override fun update(id: Long, data: Category): Category? {
        return categoryRepository.save(data)
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
