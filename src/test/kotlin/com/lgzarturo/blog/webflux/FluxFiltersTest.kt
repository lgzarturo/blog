package com.lgzarturo.blog.webflux

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxFiltersTest {
    private val names: List<String> = listOf("Bellami", "Bartolomeo", "Vivi", "Pika", "Brook", "Crocodile")

    @Test
    fun fluxFilterTest() {
        val namesWithB = Flux.fromIterable(names).filter { x: String? -> x?.startsWith("B") ?: false }.log()
        StepVerifier.create(namesWithB)
            .expectNext("Bellami", "Bartolomeo", "Brook")
            .verifyComplete()
    }

    @Test
    fun fluxFilterTest_byLength() {
        val namesWithB = Flux.fromIterable(names).filter { x: String? -> x?.length!! > 5 }.log()
        StepVerifier.create(namesWithB)
            .expectNext("Bellami", "Bartolomeo", "Crocodile")
            .verifyComplete()
    }
}
