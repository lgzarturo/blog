package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>
