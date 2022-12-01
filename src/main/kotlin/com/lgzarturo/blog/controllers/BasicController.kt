package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.services.BasicCrud
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

abstract class BasicController<T, ID>(private val basicCrud: BasicCrud<T, ID>) {
    @GetMapping
    open fun all() = basicCrud.all()

    @PostMapping
    open fun create(@RequestBody body: T): ResponseEntity<T> {
        val entity = basicCrud.create(body)
        return ResponseEntity.status(if (entity == null) HttpStatus.BAD_REQUEST else HttpStatus.CREATED).body(entity)
    }

    @GetMapping("/{id}")
    open fun read(@PathVariable id: ID): ResponseEntity<T> {
        val entity = basicCrud.read(id)
        return ResponseEntity.status(if (entity == null) HttpStatus.NOT_FOUND else HttpStatus.OK).body(entity)
    }

    @PutMapping("/{id}")
    open fun update(@PathVariable id: ID, @RequestBody body: T): ResponseEntity<T> {
        basicCrud.read(id)?: return ResponseEntity.notFound().build()
        val entity = basicCrud.update(id, body)
        return ResponseEntity.status(if (entity == null) HttpStatus.BAD_REQUEST else HttpStatus.OK).body(entity)
    }

    @DeleteMapping("/{id}")
    open fun delete(@PathVariable id: ID): Any {
        try {
            basicCrud.delete(id)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build<T>()
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<T>()
    }
}
