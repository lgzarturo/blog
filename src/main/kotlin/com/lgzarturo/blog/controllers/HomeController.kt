package com.lgzarturo.blog.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
class HomeController {

    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun index(): Response {
        log.debug("Procesando la solicitud GET de la función index()")
        return Response(
            status = HttpStatus.OK.value(),
            message = "Hola mundo"
        )
    }
}

class Response(
    var status: Int? = null,
    var message: String? = null
)
