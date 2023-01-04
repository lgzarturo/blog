package com.lgzarturo.blog.models.dtos

import org.springframework.http.HttpStatus
import java.util.*

class PeopleResponse(
    var id: String? = null,
    var status: Int? = null,
    var name: String? = null,
    var age: Int? = null,
    var message: String? = null,
    var isActive: Boolean = false
) {
    constructor(request: PeopleRequest?) : this() {
        id = UUID.randomUUID().toString()
        status = HttpStatus.CREATED.value()
        name = request?.name?:""
        age = request?.age?:0
        message = "Objeto creado"
        isActive = false
    }

    fun update(request: PeopleRequest): PeopleResponse {
        name = request.name
        age = request.age
        return this
    }
}
