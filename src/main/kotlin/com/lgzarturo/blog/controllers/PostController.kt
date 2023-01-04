package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.services.impl.JpaPostService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("posts")
class PostController(postService: JpaPostService): BasicController<Post, Long>(postService)
