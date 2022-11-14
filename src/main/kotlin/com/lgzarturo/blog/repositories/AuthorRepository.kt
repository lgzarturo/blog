package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>
