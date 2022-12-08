package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.entities.Post
import com.lgzarturo.blog.services.impl.PostServiceJpa
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("posts")
class PostController(private val postService: PostServiceJpa): BasicController<Post, Long>(postService)
