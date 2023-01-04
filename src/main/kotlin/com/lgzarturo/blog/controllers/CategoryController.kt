package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.Category
import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.services.impl.JpaCategoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("categories")
class CategoryController(
    private val categoryService: JpaCategoryService
): BasicController<Category, Long>(categoryService) {

    @GetMapping("{id}/posts")
    fun getPostsByAuthor(@PathVariable id: Long): List<Post> {
        val response = categoryService.read(id)
        return response!!.posts
    }
}
