package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long>
