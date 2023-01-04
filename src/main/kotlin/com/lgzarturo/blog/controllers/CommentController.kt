package com.lgzarturo.blog.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
@RequestMapping("comments")
class CommentController {

    @GetMapping
    fun all(): Flux<Int> {
        return Flux.just(1,2,3,4,5,6,7,8,9,10,11,12).log()
    }

    @GetMapping("/stream", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    fun stream(): Flux<Int> {
        return Flux.just(1,2,3,4).delayElements(Duration.ofSeconds(1)).log()
    }
}
