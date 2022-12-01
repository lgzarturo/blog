package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.Post
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("posts")
class PostController {
    @GetMapping
    fun list(): ResponseEntity<List<Post>> {
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun create(): ResponseEntity<Post> {
        return ResponseEntity.noContent().build()
    }

    @GetMapping("{id}")
    fun read(@PathVariable id: Long): ResponseEntity<Post> {
        return ResponseEntity.noContent().build()
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long): ResponseEntity<Post> {
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.noContent().build()
    }
}
