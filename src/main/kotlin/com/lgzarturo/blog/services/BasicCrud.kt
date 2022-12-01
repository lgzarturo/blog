package com.lgzarturo.blog.services

interface BasicCrud<T, ID> {
    fun all(): List<T>
    fun create(data: T): T?
    fun read(id: ID): T?
    fun update(id: ID, data: T): T?
    fun delete(id: ID)
}
