package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.dtos.PostRequest
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.repositories.PostRepository
import com.lgzarturo.blog.services.PostService
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostServiceJpa(
    private val postRepository: PostRepository,
    private val modelMapper: ModelMapper
) : PostService {

    override fun getAll(): List<Post> {
        return postRepository.findAll()
    }

    override fun getById(id: Long): Post? {
        return findById(id).orElse(null)
    }

    override fun save(postRequest: PostRequest): Post? {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, postRequest: PostRequest): Post? {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        try {
            val post = findById(id).get()
            postRepository.delete(post)
        } catch (error: Exception) {
            val message = "Error al intentar eliminar el registro con el id:$id"
            throw Exception(message)
        }
    }

    private fun findById(id: Long): Optional<Post> {
        return postRepository.findById(id)
    }
}
