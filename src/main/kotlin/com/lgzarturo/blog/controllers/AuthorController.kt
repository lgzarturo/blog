package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.Author
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.models.dtos.AuthorRequest
import com.lgzarturo.blog.services.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("authors")
class AuthorController(private val authorService: AuthorService) {

    /**
     * Se usa a la entidad Author como datos json (payload) en el parámetro Body y
     * para la respuesta del endpoint.
     * Author -> request, response
     */

    @GetMapping
    fun list(): ResponseEntity<List<Author>> {
        return ResponseEntity.ok(authorService.getAll())
    }

    @GetMapping("{id}/posts")
    fun getPostsByAuthor(@PathVariable id: Long): ResponseEntity<List<Post>> {
        val response = authorService.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response.posts)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: AuthorRequest): ResponseEntity<Author> {
        return ResponseEntity.ok(authorService.save(request))
    }

    @GetMapping("{id}")
    fun read(@PathVariable id: Long): ResponseEntity<Author> {
        val response = authorService.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: AuthorRequest): ResponseEntity<Author> {
        return ResponseEntity.ok(authorService.update(id, request))
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            authorService.delete(id)
        } catch (ex: Exception) {
            return ResponseEntity.unprocessableEntity().build()
        }
        return ResponseEntity.noContent().build()
    }
}
