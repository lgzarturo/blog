package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.entities.Author
import com.lgzarturo.blog.entities.Post
import com.lgzarturo.blog.repositories.AuthorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("authors")
class AuthorController(private val authorRepository: AuthorRepository) {

    /**
     * Se usa a la entidad Author como datos json (payload) en el parámetro Body y
     * para la respuesta del endpoint.
     * Author -> request, response
     */

    @GetMapping
    fun list(): ResponseEntity<List<Author>> {
        return ResponseEntity.ok(authorRepository.findAll())
    }

    @GetMapping("{id}/posts")
    fun getPostsByAuthor(@PathVariable id: Long): ResponseEntity<List<Post>> {
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response.posts)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: Author): ResponseEntity<Author> {
        return ResponseEntity.ok(authorRepository.save(request))
    }

    @GetMapping("{id}")
    fun read(@PathVariable id: Long): ResponseEntity<Author> {
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody request: Author): ResponseEntity<Author> {
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        // Modificar las propiedades del objeto a editar
        response.name = request.name
        response.description = request.description
        response.avatarImage = request.avatarImage
        return ResponseEntity.ok(authorRepository.save(response))
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        try {
            authorRepository.delete(response)
        } catch (ex: Exception) {
            return ResponseEntity.unprocessableEntity().build()
        }
        return ResponseEntity.noContent().build()
    }

    /**
     * Función privada para hacer más legible el código y no estar llamando al repositorio
     */
    private fun getById(id: Long): Author? {
        return authorRepository.findByIdOrNull(id)
    }
}
