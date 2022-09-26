package com.lgzarturo.blog.services

import com.lgzarturo.blog.entities.Category
import com.lgzarturo.blog.entities.Post
import com.lgzarturo.blog.models.CategoryRequest

interface CategoryService {
    fun getAll(): List<Category>
    fun getPostsByCategory(id: Long): List<Post>
    fun getById(id: Long): Category?
    fun save(categoryRequest: CategoryRequest): Category?
    fun update(id: Long, categoryRequest: CategoryRequest): Category?
    fun delete(id: Long)
}
