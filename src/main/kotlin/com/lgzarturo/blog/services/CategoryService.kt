package com.lgzarturo.blog.services

import com.lgzarturo.blog.entities.Category
import com.lgzarturo.blog.entities.Post

interface CategoryService {
    fun getAll(): List<Category>
    fun getPostsByCategory(id: Long): List<Post>
    fun getById(id: Long): Category?
    fun save(category: Category): Category?
    fun update(id: Long, category: Category): Category?
    fun delete(id: Long)
}
