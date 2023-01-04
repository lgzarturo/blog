package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.models.dtos.PeopleRequest
import com.lgzarturo.blog.models.dtos.PeopleResponse
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
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 1", 30, "Programador web", false),
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 2", 18, "Diseñador gráfico", true),
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 3", 22, "Administrador", true),
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 4", 17, "Estudiante", false),
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 5", 44, "Gerente", true),
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 6", 53, "Director", true),
        PeopleResponse(UUID.randomUUID().toString(), 200, "Person 7", 13, "Programador mobile", true),
    )

    @GetMapping
    fun index(@RequestParam name: String?): PeopleResponse {
        log.debug("Procesando la solicitud GET de la función index() name={}", name)
        val message = "Hola mundo ${name?:"!"}"
        return PeopleResponse(
            status = HttpStatus.OK.value(),
            message = message
        )
    }

    @PostMapping
    fun index(@RequestBody request: PeopleRequest?): PeopleResponse {
        var message = "Hola mundo"
        if (request != null) {
            message += ", tenemos un mensaje de ${request.name} y tiene ${request.age} años de antigüedad."
        }
        return PeopleResponse(
            status = HttpStatus.OK.value(),
            message = message
        )
    }

    /** CRUD Básico **/
    @PostMapping("people")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PeopleRequest): ResponseEntity<PeopleResponse> {
        log.debug("Crear una entidad")
        val response = PeopleResponse(request)
        people.add(response)
        return ResponseEntity.ok(response)
    }

    @GetMapping("people/{id}")
    fun read(@PathVariable id: String): ResponseEntity<PeopleResponse> {
        log.debug("Obtener una determinada entidad")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @PutMapping("people/{id}")
    fun update(@PathVariable id: String, @RequestBody request: PeopleRequest): ResponseEntity<PeopleResponse> {
        log.debug("Editar una determinada entidad")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response.update(request))
    }

    @DeleteMapping("people/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String): ResponseEntity<PeopleResponse> {
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
    fun list(): ResponseEntity<List<PeopleResponse>> {
        log.debug("Obtener un listado de objetos")
        return ResponseEntity.ok(people)
    }

    /** Acciones determinadas **/
    @PatchMapping("people/{id}/activate")
    fun setActive(@PathVariable id: String): ResponseEntity<PeopleResponse> {
        log.debug("Actualizar una propiedad en especifico")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        response.isActive = true
        return ResponseEntity.ok(response)
    }

    @PatchMapping("people/{id}/deactivate")
    fun setInactive(@PathVariable id: String): ResponseEntity<PeopleResponse> {
        log.debug("Actualizar una propiedad en especifico")
        val response = getById(id) ?: return ResponseEntity.notFound().build()
        response.isActive = false
        return ResponseEntity.ok(response)
    }

    /** Métodos privados **/
    private fun getById(id: String): PeopleResponse? {
        return people.find { it.id == id }
    }
}
