package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.repositories.PostRepository
import com.lgzarturo.blog.services.BasicCrud
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class JpaPostService(
    private val postRepository: PostRepository,
    private val modelMapper: ModelMapper
) : BasicCrud<Post, Long> {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun all(): List<Post> {
        return postRepository.findAll()
    }

    override fun create(data: Post): Post? {
        return try {
            postRepository.save(data)
        } catch (e: Exception) {
            log.error("Error saving post ${e.message}")
            null
        }
    }

    override fun read(id: Long): Post? {
        return findById(id).orElse(null)
    }

    override fun update(id: Long, data: Post): Post? {
        return postRepository.save(data)
    }

    override fun delete(id: Long) {
        postRepository.deleteById(id)
    }

    private fun findById(id: Long): Optional<Post> {
        return postRepository.findById(id)
    }
}
