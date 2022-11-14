package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.Post
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
