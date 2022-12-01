package com.lgzarturo.blog.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping
@RestController
class HomeController {

    private val log = LoggerFactory.getLogger(this::class.java)

    // HACK: Para propósitos de ejemplo se usa una lista mutable en memoria.
    val people = mutableListOf(
        Response(UUID.randomUUID().toString(), 200, "Person 1", 30, "Programador web", false),
        Response(UUID.randomUUID().toString(), 200, "Person 2", 18, "Diseñador gráfico", true),
        Response(UUID.randomUUID().toString(), 200, "Person 3", 22, "Administrador", true),
        Response(UUID.randomUUID().toString(), 200, "Person 4", 17, "Estudiante", false),
        Response(UUID.randomUUID().toString(), 200, "Person 5", 44, "Gerente", true),
        Response(UUID.randomUUID().toString(), 200, "Person 6", 53, "Director", true),
        Response(UUID.randomUUID().toString(), 200, "Person 7", 13, "Programador mobile", true),
    )

    @GetMapping
    fun index(@RequestParam name: String?): Response {
        log.debug("Procesando la solicitud GET de la función index() name={}", name)
        val message = "Hola mundo ${name?:"!"}"
        return Response(
            status = HttpStatus.OK.value(),
            message = message
        )
    }

    @PostMapping
    fun index(@RequestBody request: Request?): Response {
        var message = "Hola mundo"
        if (request != null) {
            message += ", tenemos un mensaje de ${request.name} y tiene ${request.age} años de antigüedad."
        }
        return Response(
            status = HttpStatus.OK.value(),
            message = message
        )
    }

    /** CRUD Básico **/
    @PostMapping("people")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: Request): ResponseEntity<Response> {
        log.debug("Crear una entidad")
        val response = Response(request)
        people.add(response)
        return ResponseEntity.ok(response)
    }

    @GetMapping("people/{id}")
    fun read(@PathVariable id: String): ResponseEntity<Response> {
        log.debug("Obtener una determinada entidad")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @PutMapping("people/{id}")
    fun update(@PathVariable id: String, @RequestBody request: Request): ResponseEntity<Response> {
        log.debug("Editar una determinada entidad")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response.update(request))
    }

    @DeleteMapping("people/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String): ResponseEntity<Response> {
        log.debug("Borrar una determinada entidad")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        try {
            people.removeAt(people.indexOf(response))
        } catch (ex: Exception) {
            return ResponseEntity.unprocessableEntity().build()
        }
        return ResponseEntity.noContent().build()
    }

    /** Listado de elementos **/
    @GetMapping("people")
    fun list(): ResponseEntity<List<Response>> {
        log.debug("Obtener un listado de objetos")
        return ResponseEntity.ok(people)
    }

    /** Acciones determinadas **/
    @PatchMapping("people/{id}/activate")
    fun setActive(@PathVariable id: String): ResponseEntity<Response> {
        log.debug("Actualizar una propiedad en especifico")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        response.isActive = true
        return ResponseEntity.ok(response)
    }

    @PatchMapping("people/{id}/deactivate")
    fun setInactive(@PathVariable id: String): ResponseEntity<Response> {
        log.debug("Actualizar una propiedad en especifico")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        response.isActive = false
        return ResponseEntity.ok(response)
    }

    /** Métodos privados **/
    private fun getById(id: String): Response? {
        return people.find { it.id == id }
    }
}

class Response(
    var id: String? = null,
    var status: Int? = null,
    var name: String? = null,
    var age: Int? = null,
    var message: String? = null,
    var isActive: Boolean = false
) {
    constructor(request: Request?) : this() {
        id = UUID.randomUUID().toString()
        status = HttpStatus.CREATED.value()
        name = request?.name?:""
        age = request?.age?:0
        message = "Objeto creado"
        isActive = false
    }

    fun update(request: Request): Response {
        name = request.name
        age = request.age
        return this
    }
}

class Request {
    var name: String? = null
    var age: Int? = null
}
