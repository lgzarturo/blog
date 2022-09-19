package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.entities.LikeCounter
import org.springframework.data.jpa.repository.JpaRepository

interface LikeCounterRepository : JpaRepository<LikeCounter, Long>
