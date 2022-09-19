package com.lgzarturo.blog.repositories

import com.lgzarturo.blog.entities.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>
