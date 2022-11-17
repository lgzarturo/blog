package com.lgzarturo.blog.services

import com.lgzarturo.blog.models.entities.Author
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.models.dtos.AuthorRequest

interface AuthorService {
    fun getAll(): List<Author>
    fun getPostsByAuthor(id: Long): List<Post>
    fun getById(id: Long): Author?
    fun save(authorRequest: AuthorRequest): Author?
    fun update(id: Long, authorRequest: AuthorRequest): Author?
    fun delete(id: Long)
}
