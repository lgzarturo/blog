package com.lgzarturo.blog.services

import com.lgzarturo.blog.entities.Author
import com.lgzarturo.blog.entities.Post

interface AuthorService {
    fun getAll(): List<Author>
    fun getPostsByAuthor(id: Long): List<Post>
    fun getById(id: Long): Author?
    fun save(author: Author): Author?
    fun update(id: Long, author: Author): Author?
    fun delete(id: Long)
}
