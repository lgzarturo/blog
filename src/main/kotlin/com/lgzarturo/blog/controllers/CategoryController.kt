package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.entities.Category
import com.lgzarturo.blog.entities.Post
import com.lgzarturo.blog.models.CategoryRequest
import com.lgzarturo.blog.services.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("categories")
class CategoryController(private val categoryService: CategoryService) {
    @GetMapping
    fun list(): ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryService.getAll())
    }

    @GetMapping("{id}/posts")
    fun getPostsByAuthor(@PathVariable id: Long): ResponseEntity<List<Post>> {
        val response = categoryService.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response.posts)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CategoryRequest): ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.save(request))
    }

    @GetMapping("{id}")
    fun read(@PathVariable id: Long): ResponseEntity<Category> {
        val response = categoryService.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: CategoryRequest): ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.update(id, request))
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            categoryService.delete(id)
        } catch (ex: Exception) {
            return ResponseEntity.unprocessableEntity().build()
        }
        return ResponseEntity.noContent().build()
    }
}
