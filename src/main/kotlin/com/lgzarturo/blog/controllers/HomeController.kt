package com.lgzarturo.blog.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
class HomeController {

    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun index(@RequestBody request: Request?, @RequestParam name: String?): Response {
        log.debug("Procesando la solicitud GET de la función index() name={}", name)
        var message = "Hola mundo ${name?:"!"}"
        if (request != null) {
            message += ", tenemos un mensaje de ${request.name} y tiene ${request.age} años de antigüedad."
        }
        return Response(
            status = HttpStatus.OK.value(),
            message = message
        )
    }
}

class Response(
    var status: Int? = null,
    var message: String? = null
)

class Request(
    var name: String? = null,
    var age: Int? = null
)
