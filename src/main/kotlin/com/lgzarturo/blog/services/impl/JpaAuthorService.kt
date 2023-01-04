package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.models.dtos.AuthorRequest
import com.lgzarturo.blog.models.entities.Author
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.repositories.AuthorRepository
import com.lgzarturo.blog.services.AuthorService
import org.modelmapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
class JpaAuthorService(
    private val authorRepository: AuthorRepository,
    private val modelMapper: ModelMapper
    ) : AuthorService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<Author> {
        log.trace("Obteniendo todos los autores")
        return authorRepository.findAll()
    }

    override fun getPostsByAuthor(id: Long): List<Post> {
        log.trace("Obteniendo los artículos del author con el id:$id")
        val author = findById(id).orElse(null) ?: return emptyList()
        return author.posts
    }

    override fun getById(id: Long): Author? {
        log.trace("Obteniendo el author con el id:$id")
        return findById(id).orElse(null)
    }

    override fun save(authorRequest: AuthorRequest): Author? {
        log.trace("Guardando datos del author")
        val author = modelMapper.map(authorRequest, Author::class.java)
        return authorRepository.save(author)
    }

    override fun update(id: Long, authorRequest: AuthorRequest): Author? {
        log.trace("Actualizar el author con el id:$id")
        val authorPersisted = findById(id).orElse(null) ?: return null
        BeanUtils.copyProperties(authorRequest, authorPersisted)
        return authorRepository.save(authorPersisted)
    }

    @Throws(Exception::class)
    override fun delete(id: Long) {
        log.trace("Borrando el author con el id:$id")
        try {
            val author = findById(id).get()
            authorRepository.delete(author)
        } catch (error: Exception) {
            val message = "Error al intentar eliminar el registro con el id:$id"
            log.error(message, error)
            throw Exception(message)
        }
    }

    private fun findById(id: Long): Optional<Author> {
        return authorRepository.findById(id)
    }
}
