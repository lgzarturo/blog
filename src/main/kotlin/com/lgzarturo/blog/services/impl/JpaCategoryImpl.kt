package com.lgzarturo.blog.services.impl

import com.github.slugify.Slugify
import com.lgzarturo.blog.entities.Category
import com.lgzarturo.blog.entities.Post
import com.lgzarturo.blog.repositories.CategoryRepository
import com.lgzarturo.blog.services.CategoryService
import org.springframework.stereotype.Service
import java.util.*

@Service
class JpaCategoryImpl(private val categoryRepository: CategoryRepository) : CategoryService {
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

    override fun save(category: Category): Category? {
        category.slug = category.title?.let { generateSlugifyTitle(it) }
        return categoryRepository.save(category)
    }

    override fun update(id: Long, category: Category): Category? {
        val categoryPersisted = findById(id).orElse(null) ?: return null
        categoryPersisted.title = category.title
        categoryPersisted.slug = category.title?.let { generateSlugifyTitle(it) }
        categoryPersisted.description = category.description
        return save(categoryPersisted)
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

    private fun generateSlugifyTitle(title: String): String {
        val slug = Slugify.builder().locale(Locale.ENGLISH).build()
        return slug.slugify(title)
    }
}
