package com.lgzarturo.blog.services

import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.models.requests.PostRequest

interface PostService {
    fun getAll(): List<Post>
    fun getById(id: Long): Post?
    fun save(postRequest: PostRequest): Post?
    fun update(id: Long, postRequest: PostRequest): Post?
    fun delete(id: Long)
}