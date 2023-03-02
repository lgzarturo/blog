package com.lgzarturo.blog.controllers

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.lgzarturo.blog.services.MyBean
import com.lgzarturo.blog.services.MyComponent
import com.lgzarturo.blog.services.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/test")
class ApiRouteController(
    private val orderService: OrderService,
    private val myBean: MyBean,
    private val myComponent: MyComponent
    ) {

    private val logger: Logger = LoggerFactory.getLogger(ApiRouteController::class.java)

    /**
     * Ejemplo para llamar un bean en el controlador
     */
    @GetMapping("/hello")
    fun hello(): String {
        logger.debug("Estoy en /hello")
        myBean.greetings()
        myComponent.greetingFromComponent()
        return "Hello world!"
    }

    /**
     * Obtener parámetros de la URI como QueryString Opcionales y PathVariables.
     */
    @GetMapping("/article/{id}")
    fun getArticleById(
        @PathVariable id: Long,
        @RequestParam params: String,
        @RequestParam(required = false, defaultValue = "") editorial: String
    ): String {
        logger.debug("Estoy en /article/{id}?params={params}")
        return "Leyendo el articulo $id, params $params, editorial: $editorial"
    }

    /**
     * Usar parámetros por medio de valores en la URI.
     */
    @GetMapping("/article/{id}/author/{username}")
    fun getArticleByIdAndAndAuthorUsername(
        @PathVariable id: Long,
        @PathVariable username: String
    ): String {
        logger.debug("Estoy en /article/{id}/author/{username}")
        return "Leyendo el articulo $id y el autor es $username"
    }

    /**
     * Obtener parámetros por medio de un map con clave y valor.
     */
    @PostMapping("/data")
    fun postData(@RequestBody data: Map<String, Any>): String {
        data.keys.forEach {key ->
            val value = data[key]
            logger.debug("$key -> $value")
        }
        return "Saved article"
    }

    /**
     * Ejemplo para pasar un Objeto por medio del payload.
     */
    @PostMapping("/article")
    fun postArticle(@RequestBody article: Article): String {
        logger.debug("postArticle")
        logger.debug("article: $article")
        logger.debug("title: ${article.title}")
        logger.debug("author: ${article.author}")
        logger.debug("url: ${article.url}")
        logger.debug("body: ${article.body}")
        article.timestamp = LocalDateTime.now()
        return "Saved article : ${article.title}, created at: ${article.timestamp}"
    }

    data class Article(
        val title: String,
        val author: String,
        val url: String,
        val body: String,
        var timestamp: LocalDateTime?
    )

    /**
     * Aplicar una redirección con la anotación @ResponseStatus
     */
    @GetMapping("/v1/articles")
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY, reason = "Se ha movido a la versión 2 de la API")
    fun movedEndpoint(): String {
        return "Articulo versión 1"
    }

    /**
     * Usar un parámetro en la URI para determinar la respuesta del API con ResponseEntity
     */
    @GetMapping("/v1/animals/{location}")
    fun getAnimals(@PathVariable location: String): ResponseEntity<List<String>> {
        if (location == "farm") {
            return ResponseEntity.status(HttpStatus.OK).body(listOf("cow", "chicken"))
        } else if (location == "forest") {
            return ResponseEntity.status(HttpStatus.OK).body(listOf("jaguar", "puma"))
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    /**
     * Generar una excepción de ejemplo con NullPointerException
     */
    @GetMapping("/calculate/{number}")
    fun calculate(@PathVariable number: Int) {
        throw NullPointerException("This exception are generated when trying to calculate a number")
    }

    /**
     * Generar una excepción de ejemplo con RuntimeException
     */
    @GetMapping("/calculate2/{number}")
    fun calculate2(@PathVariable number: Int) {
        throw RuntimeException("This exception are generated when trying to calculate a number")
    }

    /**
     * Ejemplo para responder con un documento JSON agregando el Header Content-Type
     */
    @GetMapping("/user/data")
    fun getUserData(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK)
            .header("Content-Type", "application/json")
            .body("{\"username\": \"johndoe\", \"salted\": true, \"password\": \"**********\"}")
    }

    /**
     * Retornar un json por medio de un map con clave valor
     */
    @GetMapping("/user/data/map")
    fun getUserDataFromMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["username"] = "johndoe"
        map["password"] = "**********"
        map["salted"] = true
        return map
    }

    @GetMapping("/user")
    fun getUser(): ResponseEntity<UserData> {
        return ResponseEntity.ok(UserData("johndoe", "****", false))
    }

    data class UserData(
        val username: String,
        @JsonIgnore
        val password: String,
        @JsonProperty("is_password_encrypted")
        val salted: Boolean
    ) {
        // @JsonValue <- Remplaza la serialización del objeto
        @JsonGetter("information")
        fun info(): String {
            return "Username is: $password, and salted password is encrypted: $salted"
        }
    }

    @PostMapping("/order")
    fun createOrder(@RequestBody products: List<Product>): ResponseEntity<String> {
        orderService.saveOrder(products)
        return ResponseEntity.ok("Se ha guardado la orden")
    }

    data class Product(
        val name: String,
        val price: Double
    )
}
