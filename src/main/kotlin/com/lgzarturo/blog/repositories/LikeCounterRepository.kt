package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.models.entities.LikeCounter
import org.springframework.data.jpa.repository.JpaRepository

interface LikeCounterRepository : JpaRepository<LikeCounter, Long>
