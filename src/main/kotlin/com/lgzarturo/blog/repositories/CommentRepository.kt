package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
